package com.recviz;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Drives one visualization run: instrument the source, drop a {@code Tracer.java}
 * template next to it, compile both with the in-process JDK compiler, execute the
 * program with inherited stdio (so solutions reading {@code Scanner} work as usual),
 * and leave the produced trace JSON at the requested output path.
 */
public final class Runner {

    public record RunResult(Path tracePath, int exitCode, List<String> recursiveMethods) {}

    private Runner() {}

    public static RunResult run(Path sourceFile, Path outFile, long maxEvents)
            throws IOException, InterruptedException {
        String source = Files.readString(sourceFile);
        Instrumenter.Result res = Instrumenter.instrument(source);
        if (res.recursiveMethods().isEmpty()) {
            throw new IllegalStateException("No recursive methods found in " + sourceFile);
        }
        System.out.println("[recviz] instrumented recursive methods: "
                + String.join(", ", res.recursiveMethods()));

        Path work = Files.createTempDirectory("recviz-");
        try {
            String pkg = res.packageName();
            Path pkgDir = pkg == null ? work : work.resolve(pkg.replace('.', '/'));
            Files.createDirectories(pkgDir);

            Path instrumentedFile = pkgDir.resolve(res.className() + ".java");
            Files.writeString(instrumentedFile, res.instrumentedSource());

            String tracerSource;
            try (InputStream in = Runner.class.getResourceAsStream("/recviz/Tracer.java")) {
                if (in == null) {
                    throw new IOException("Tracer template missing from the recviz jar");
                }
                tracerSource = new String(in.readAllBytes());
            }
            if (pkg != null) {
                tracerSource = "package " + pkg + ";\n\n" + tracerSource;
            }
            Files.writeString(pkgDir.resolve("Tracer.java"), tracerSource);

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            if (compiler == null) {
                throw new IllegalStateException(
                        "No system Java compiler found — run recviz with a JDK, not a JRE");
            }
            int cc = compiler.run(null, System.out, System.err,
                    "-d", work.toString(),
                    instrumentedFile.toString(),
                    pkgDir.resolve("Tracer.java").toString());
            if (cc != 0) {
                throw new IllegalStateException("Compilation of the instrumented program failed (javac exit " + cc + ")");
            }

            String mainClass = pkg == null ? res.className() : pkg + "." + res.className();
            String javaBin = Paths.get(System.getProperty("java.home"), "bin",
                    isWindows() ? "java.exe" : "java").toString();
            Path absOut = outFile.toAbsolutePath();
            if (absOut.getParent() != null) {
                Files.createDirectories(absOut.getParent());
            }
            ProcessBuilder pb = new ProcessBuilder(javaBin,
                    "-cp", work.toString(),
                    "-Drecviz.out=" + absOut,
                    "-Drecviz.prog=" + res.className(),
                    "-Drecviz.maxEvents=" + maxEvents,
                    mainClass);
            pb.inheritIO(); // forward stdin for Scanner-based solutions, show program output
            int exit = pb.start().waitFor();

            if (!Files.exists(absOut)) {
                throw new IllegalStateException("The program finished (exit " + exit
                        + ") but no trace was written — the Tracer shutdown hook did not run");
            }
            String head = Files.readString(absOut);
            if (head.contains("\"events\":0")) {
                System.out.println("[recviz] WARNING: trace has 0 events — the recursive method(s) ["
                        + String.join(", ", res.recursiveMethods())
                        + "] were never called at runtime. Check that main() actually reaches them.");
            }
            return new RunResult(absOut, exit, res.recursiveMethods());
        } finally {
            try (Stream<Path> walk = Files.walk(work)) {
                walk.sorted(Comparator.reverseOrder()).forEach(p -> {
                    try {
                        Files.deleteIfExists(p);
                    } catch (IOException ignored) {
                        // best effort cleanup of the temp workspace
                    }
                });
            } catch (IOException ignored) {
                // best effort cleanup
            }
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name", "").toLowerCase().contains("win");
    }
}
