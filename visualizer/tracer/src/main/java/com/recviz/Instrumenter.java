package com.recviz;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Source-to-source transformer: parses a single-file Java solution, finds methods that
 * are on a recursion cycle (direct self-recursion or mutual recursion through other
 * same-file methods), and injects {@code Tracer.enter}/{@code Tracer.exit} calls so the
 * recursive call tree is recorded at runtime.
 *
 * <p>Every {@code return expr;} inside an instrumented method becomes
 * <pre>
 * {
 *     var __recvizRet = expr;
 *     Tracer.exit(__recvizId, __recvizRet);
 *     return __recvizRet;
 * }
 * </pre>
 * {@code var} keeps primitive return types intact. Nested recursive calls inside the
 * returned expression need no special handling: they nest naturally on the tracer's
 * call stack while the expression is evaluated, before the outer {@code exit}.
 */
public final class Instrumenter {

    public record Result(String instrumentedSource, String className, String packageName,
                         List<String> recursiveMethods) {}

    private Instrumenter() {}

    public static Result instrument(String source) {
        ParserConfiguration cfg = new ParserConfiguration()
                .setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_21);
        JavaParser parser = new JavaParser(cfg);
        CompilationUnit cu = parser.parse(source).getResult()
                .orElseThrow(() -> new IllegalArgumentException("Could not parse the source file"));

        List<MethodDeclaration> methods = cu.findAll(MethodDeclaration.class);

        // Same-file call graph by method name (overloads are merged on purpose: a
        // name-based graph may over-approximate but never misses a recursive method).
        Map<String, List<MethodDeclaration>> byName = new HashMap<>();
        for (MethodDeclaration md : methods) {
            byName.computeIfAbsent(md.getNameAsString(), k -> new ArrayList<>()).add(md);
        }
        Map<MethodDeclaration, Set<String>> calls = new IdentityHashMap<>();
        for (MethodDeclaration md : methods) {
            Set<String> called = new HashSet<>();
            md.walk(MethodCallExpr.class, mc -> {
                if (byName.containsKey(mc.getNameAsString())) {
                    called.add(mc.getNameAsString());
                }
            });
            calls.put(md, called);
        }
        Map<String, Set<String>> graph = new HashMap<>();
        calls.forEach((md, callees) ->
                graph.computeIfAbsent(md.getNameAsString(), k -> new HashSet<>()).addAll(callees));

        // A method is recursive iff its name is reachable from itself in the graph.
        Set<String> recursive = new HashSet<>();
        for (String name : graph.keySet()) {
            Deque<String> work = new ArrayDeque<>(graph.getOrDefault(name, Set.of()));
            Set<String> seen = new HashSet<>();
            while (!work.isEmpty()) {
                String cur = work.pop();
                if (cur.equals(name)) {
                    recursive.add(name);
                    break;
                }
                if (seen.add(cur)) {
                    work.addAll(graph.getOrDefault(cur, Set.of()));
                }
            }
        }

        List<String> instrumented = new ArrayList<>();
        for (MethodDeclaration md : methods) {
            if (recursive.contains(md.getNameAsString()) && md.getBody().isPresent()) {
                instrumentMethod(md);
                instrumented.add(md.getNameAsString());
            }
        }

        // Primary type: the public top-level type, else the first top-level type.
        // (getPrimaryTypeName() is unreliable here because the CU has no file storage.)
        var primaryType = cu.getTypes().stream()
                .filter(t -> t.isPublic())
                .findFirst()
                .or(() -> cu.getTypes().stream().findFirst())
                .orElseThrow(() -> new IllegalArgumentException("No top-level class found"));
        String className = primaryType.getNameAsString();

        // Force Tracer class initialization (which registers the shutdown hook that
        // writes the trace) even if no recursive call happens at runtime — e.g. when
        // main() exercises an iterative alternative instead of the recursive method.
        // Placed first so it runs before any user static initializers.
        if (primaryType instanceof ClassOrInterfaceDeclaration coid && !coid.isInterface()) {
            BlockStmt pingBody = new BlockStmt();
            pingBody.addStatement(StaticJavaParser.parseStatement("Tracer.ping();"));
            coid.getMembers().add(0, new InitializerDeclaration(true, pingBody));
        }
        String packageName = cu.getPackageDeclaration()
                .map(pd -> pd.getNameAsString())
                .orElse(null);
        return new Result(cu.toString(), className, packageName, instrumented);
    }

    private static void instrumentMethod(MethodDeclaration md) {
        BlockStmt body = md.getBody().orElseThrow();
        String idVar = "__recvizId";

        StringBuilder names = new StringBuilder();
        StringBuilder values = new StringBuilder();
        for (Parameter p : md.getParameters()) {
            if (names.length() > 0) {
                names.append(", ");
                values.append(", ");
            }
            names.append('"').append(p.getNameAsString()).append('"');
            values.append(p.getNameAsString());
        }
        String enterSrc = "long " + idVar + " = Tracer.enter(\"" + md.getNameAsString() + "\""
                + ", new String[]{" + names + "}"
                + (values.length() > 0 ? ", " + values : "") + ");";
        Statement enter = StaticJavaParser.parseStatement(enterSrc);
        body.addStatement(0, enter);

        boolean isVoid = md.getType().isVoidType();

        // Rewrite returns that belong to this method directly. Returns inside lambdas
        // (which return from the lambda) or inside methods of local/anonymous classes
        // (whose nearest MethodDeclaration is the inner one) must stay untouched.
        List<ReturnStmt> returns = md.findAll(ReturnStmt.class, r ->
                r.findAncestor(MethodDeclaration.class).map(a -> a == md).orElse(false)
                        && r.findAncestor(LambdaExpr.class).isEmpty());
        for (ReturnStmt r : returns) {
            BlockStmt replacement = new BlockStmt();
            if (r.getExpression().isPresent()) {
                String expr = r.getExpression().get().toString();
                replacement.addStatement(StaticJavaParser.parseStatement("var __recvizRet = " + expr + ";"));
                replacement.addStatement(StaticJavaParser.parseStatement(
                        "Tracer.exit(" + idVar + ", __recvizRet);"));
                replacement.addStatement(StaticJavaParser.parseStatement("return __recvizRet;"));
            } else {
                replacement.addStatement(StaticJavaParser.parseStatement("Tracer.exit(" + idVar + ");"));
                replacement.addStatement(new ReturnStmt());
            }
            r.replace(replacement);
        }

        // Implicit fall-through exit for void methods.
        if (isVoid) {
            body.addStatement(StaticJavaParser.parseStatement("Tracer.exit(" + idVar + ");"));
        }
    }
}
