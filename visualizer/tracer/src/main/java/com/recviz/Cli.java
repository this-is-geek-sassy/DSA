package com.recviz;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * recviz command line entry point.
 *
 * <pre>
 *   java -jar recviz.jar run &lt;Solution.java&gt; [--out &lt;trace.json&gt;] [--max-events N]
 * </pre>
 *
 * Without {@code --out}, the trace is written to
 * {@code visualizer/web/public/traces/&lt;ClassName&gt;.json} (located by walking up from the
 * working directory) and the traces {@code manifest.json} consumed by the web UI is
 * regenerated from the directory listing.
 */
public final class Cli {

    private Cli() {}

    public static void main(String[] args) throws Exception {
        if (args.length == 0 || (args.length == 1 && ("-h".equals(args[0]) || "--help".equals(args[0])))) {
            usage();
            return;
        }
        if (!"run".equals(args[0]) || args.length < 2) {
            usage();
            System.exit(1);
        }

        Path source = Paths.get(args[1]);
        if (!Files.isRegularFile(source)) {
            System.err.println("[recviz] file not found: " + source);
            System.exit(1);
        }

        long maxEvents = 200_000;
        Path out = null;
        for (int i = 2; i < args.length; i++) {
            switch (args[i]) {
                case "--out" -> {
                    if (++i >= args.length) {
                        System.err.println("[recviz] --out requires a path");
                        System.exit(1);
                    }
                    out = Paths.get(args[i]);
                }
                case "--max-events" -> {
                    if (++i >= args.length) {
                        System.err.println("[recviz] --max-events requires a number");
                        System.exit(1);
                    }
                    maxEvents = Long.parseLong(args[i]);
                }
                default -> {
                    System.err.println("[recviz] unknown option: " + args[i]);
                    usage();
                    System.exit(1);
                }
            }
        }

        if (out == null) {
            String base = source.getFileName().toString().replaceFirst("\\.java$", "");
            out = findTracesDir().resolve(base + ".json");
        }

        System.out.println("[recviz] instrumenting " + source + " ...");
        Runner.RunResult result = Runner.run(source.toAbsolutePath(), out, maxEvents);
        updateManifest(result.tracePath().getParent());
        System.out.println("[recviz] done. trace: " + result.tracePath());
    }

    private static Path findTracesDir() {
        Path dir = Paths.get("").toAbsolutePath();
        while (dir != null) {
            Path candidate = dir.resolve(Paths.get("visualizer", "web", "public", "traces"));
            if (Files.isDirectory(candidate)) {
                return candidate;
            }
            dir = dir.getParent();
        }
        return Paths.get("").toAbsolutePath();
    }

    private static void updateManifest(Path tracesDir) {
        if (tracesDir == null || !Files.isDirectory(tracesDir)) {
            return;
        }
        List<String> names = new ArrayList<>();
        try (Stream<Path> stream = Files.list(tracesDir)) {
            stream.map(p -> p.getFileName().toString())
                    .filter(n -> n.endsWith(".json"))
                    .filter(n -> !n.equals("manifest.json"))
                    .sorted(Comparator.naturalOrder())
                    .forEach(names::add);
        } catch (IOException e) {
            System.err.println("[recviz] could not list traces dir: " + e.getMessage());
            return;
        }
        StringBuilder sb = new StringBuilder("{\"traces\":[");
        for (int i = 0; i < names.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append('"').append(names.get(i)).append('"');
        }
        sb.append("]}");
        try {
            Files.writeString(tracesDir.resolve("manifest.json"), sb.toString());
        } catch (IOException e) {
            System.err.println("[recviz] could not update manifest: " + e.getMessage());
        }
    }

    private static void usage() {
        System.out.println("""
                recviz — Java recursion tree visualizer

                Usage:
                  java -jar recviz.jar run <Solution.java> [--out <trace.json>] [--max-events N]

                The instrumented program runs with your terminal as stdin/stdout,
                so solutions reading input via Scanner work unchanged.
                """);
    }
}
