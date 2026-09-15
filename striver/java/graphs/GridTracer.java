package graphs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Records a grid BFS as a time-sliced event stream for the gridview UI.
 *
 * <pre>
 *   GridTracer.start(grid);
 *   GridTracer.vars("noFreshOranges", n, "nodeLeftInLayer", k);
 *   GridTracer.pop(r, c);
 *   GridTracer.skip(rr, cc, "oob");
 *   GridTracer.infect(rr, cc);
 *   GridTracer.tick(moveCount);
 *   GridTracer.done(ans);
 * </pre>
 *
 * Writes {@code visualizer/grid-web/public/grids/&lt;MainClass&gt;.json} on JVM exit
 * (shutdown hook) and refreshes {@code manifest.json}. No dependencies beyond the JDK.
 *
 * <p>Configuration via system properties:
 * <ul>
 *   <li>{@code gridviz.out} — output path (default: grids/&lt;program&gt;.json)</li>
 *   <li>{@code gridviz.prog} — program name recorded in metadata</li>
 *   <li>{@code gridviz.maxEvents} — event cap (default 200000)</li>
 * </ul>
 */
public final class GridTracer {

    private static final String OUT_PROP = System.getProperty("gridviz.out");
    private static final String PROG_PROP = System.getProperty("gridviz.prog");
    private static final long MAX_EVENTS =
            Long.parseLong(System.getProperty("gridviz.maxEvents", "200000"));

    private static final StringBuilder buf = new StringBuilder(1 << 16);
    private static final Map<String, Object> currentVars = new LinkedHashMap<>();
    private static final Map<String, String> legend = new LinkedHashMap<>();
    private static final List<int[]> tickRotted = new ArrayList<>();

    private static int[][] initialGrid = new int[0][0];
    private static int rows;
    private static int cols;
    private static long seq;
    private static long eventCount;
    private static boolean truncated;
    private static boolean first = true;
    private static boolean started;
    private static boolean havePop;
    private static int lastR;
    private static int lastC;
    private static int minute;
    private static Integer result;
    private static String program = "grid";

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(GridTracer::flush, "gridviz-flush"));
    }

    private GridTracer() {}

    /** Optional override of the cell-value legend written into {@code meta.legend}. */
    public static synchronized void legend(int value, String label) {
        legend.put(String.valueOf(value), label);
    }

    /**
     * Begin a trace: deep-copies {@code grid} as {@code initialGrid} and emits a
     * {@code start} event whose {@code frontier} is every cell currently valued {@code 2}.
     */
    public static synchronized void start(int[][] grid) {
        started = true;
        if (PROG_PROP != null && !PROG_PROP.isBlank()) {
            program = PROG_PROP;
        } else {
            program = callerProgram();
        }
        initialGrid = copyGrid(grid);
        rows = initialGrid.length;
        cols = rows == 0 ? 0 : initialGrid[0].length;
        if (legend.isEmpty()) {
            legend.put("0", "empty");
            legend.put("1", "fresh");
            legend.put("2", "rotten");
        }
        List<int[]> frontier = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (initialGrid[i][j] == 2) {
                    frontier.add(new int[] {i, j});
                }
            }
        }
        if (!beginEvent()) {
            return;
        }
        buf.append("{\"t\":\"start\",\"s\":").append(seq++);
        buf.append(",\"frontier\":");
        appendPairs(frontier);
        appendVars();
        buf.append('}');
    }

    /** Merge a repeating {@code (name, value)} snapshot into the inspector map. */
    public static synchronized void vars(Object... kv) {
        int n = kv.length - (kv.length % 2);
        for (int i = 0; i < n; i += 2) {
            currentVars.put(String.valueOf(kv[i]), kv[i + 1]);
        }
    }

    /** Dequeue the cell currently being processed. */
    public static synchronized void pop(int r, int c) {
        lastR = r;
        lastC = c;
        havePop = true;
        if (!beginEvent()) {
            return;
        }
        buf.append("{\"t\":\"pop\",\"s\":").append(seq++);
        buf.append(",\"r\":").append(r);
        buf.append(",\"c\":").append(c);
        buf.append(",\"minute\":").append(minute);
        appendVars();
        buf.append('}');
    }

    /**
     * Neighbor rejected. {@code why} is one of {@code oob}, {@code visited}, {@code empty}.
     * Coordinates may be out of bounds.
     */
    public static synchronized void skip(int r, int c, String why) {
        if (!beginEvent()) {
            return;
        }
        buf.append("{\"t\":\"skip\",\"s\":").append(seq++);
        buf.append(",\"r\":").append(r);
        buf.append(",\"c\":").append(c);
        if (havePop) {
            buf.append(",\"from\":[").append(lastR).append(',').append(lastC).append(']');
        }
        buf.append(",\"why\":").append(quote(why == null ? "" : why));
        buf.append(",\"minute\":").append(minute);
        appendVars();
        buf.append('}');
    }

    /** Fresh cell becomes rotten and is enqueued into the next layer. */
    public static synchronized void infect(int r, int c) {
        tickRotted.add(new int[] {r, c});
        if (!beginEvent()) {
            return;
        }
        buf.append("{\"t\":\"infect\",\"s\":").append(seq++);
        buf.append(",\"r\":").append(r);
        buf.append(",\"c\":").append(c);
        if (havePop) {
            buf.append(",\"from\":[").append(lastR).append(',').append(lastC).append(']');
        }
        buf.append(",\"minute\":").append(minute);
        appendVars();
        buf.append('}');
    }

    /**
     * Layer complete. {@code moveCount} is the minute that just finished
     * (the BFS {@code moveCount} after it is incremented).
     */
    public static synchronized void tick(int moveCount) {
        minute = moveCount;
        if (!beginEvent()) {
            return;
        }
        buf.append("{\"t\":\"tick\",\"s\":").append(seq++);
        buf.append(",\"minute\":").append(minute);
        buf.append(",\"rotted\":");
        appendPairs(tickRotted);
        appendVars();
        buf.append('}');
        tickRotted.clear();
    }

    /** Record the algorithm result. The file is still flushed on JVM exit. */
    public static synchronized void done(int ans) {
        result = ans;
        if (!beginEvent()) {
            return;
        }
        buf.append("{\"t\":\"done\",\"s\":").append(seq++);
        buf.append(",\"result\":").append(ans);
        appendVars();
        buf.append('}');
    }

    private static boolean beginEvent() {
        if (!started || truncated) {
            return false;
        }
        if (eventCount >= MAX_EVENTS) {
            truncated = true;
            return false;
        }
        if (!first) {
            buf.append(",\n    ");
        }
        first = false;
        eventCount++;
        return true;
    }

    private static void appendVars() {
        if (currentVars.isEmpty()) {
            return;
        }
        buf.append(",\"vars\":{");
        boolean firstVar = true;
        for (Map.Entry<String, Object> e : currentVars.entrySet()) {
            if (!firstVar) {
                buf.append(',');
            }
            firstVar = false;
            buf.append(quote(e.getKey())).append(':');
            serVal(e.getValue());
        }
        buf.append('}');
    }

    private static void appendPairs(List<int[]> pairs) {
        buf.append('[');
        for (int i = 0; i < pairs.size(); i++) {
            if (i > 0) {
                buf.append(',');
            }
            int[] p = pairs.get(i);
            buf.append('[').append(p[0]).append(',').append(p[1]).append(']');
        }
        buf.append(']');
    }

    private static void serVal(Object v) {
        if (v == null) {
            buf.append("null");
            return;
        }
        if (v instanceof Boolean || v instanceof Byte || v instanceof Short
                || v instanceof Integer || v instanceof Long) {
            buf.append(v);
            return;
        }
        if (v instanceof Float f) {
            if (f.isNaN() || f.isInfinite()) {
                buf.append(quote(f.toString()));
            } else {
                buf.append(f);
            }
            return;
        }
        if (v instanceof Double d) {
            if (d.isNaN() || d.isInfinite()) {
                buf.append(quote(d.toString()));
            } else {
                buf.append(d);
            }
            return;
        }
        buf.append(quote(String.valueOf(v)));
    }

    private static int[][] copyGrid(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return new int[0][0];
        }
        int m = grid.length;
        int n = grid[0] == null ? 0 : grid[0].length;
        int[][] out = new int[m][n];
        for (int i = 0; i < m; i++) {
            if (grid[i] != null) {
                int len = Math.min(n, grid[i].length);
                System.arraycopy(grid[i], 0, out[i], 0, len);
            }
        }
        return out;
    }

    private static void flush() {
        if (!started) {
            return;
        }
        Path out = resolveOut(program);
        StringBuilder doc = new StringBuilder(buf.length() + 512);
        doc.append("{\n");
        doc.append("  \"meta\": {\n");
        doc.append("    \"program\": ").append(quote(program)).append(",\n");
        doc.append("    \"kind\": \"grid-bfs\",\n");
        doc.append("    \"rows\": ").append(rows).append(",\n");
        doc.append("    \"cols\": ").append(cols).append(",\n");
        doc.append("    \"legend\": {");
        boolean firstLeg = true;
        for (Map.Entry<String, String> e : legend.entrySet()) {
            if (!firstLeg) {
                doc.append(", ");
            }
            firstLeg = false;
            doc.append(quote(e.getKey())).append(": ").append(quote(e.getValue()));
        }
        doc.append("},\n");
        if (result != null) {
            doc.append("    \"result\": ").append(result).append(",\n");
        }
        doc.append("    \"createdAt\": ").append(quote(Instant.now().toString())).append(",\n");
        doc.append("    \"events\": ").append(eventCount).append(",\n");
        doc.append("    \"maxEvents\": ").append(MAX_EVENTS).append(",\n");
        doc.append("    \"truncated\": ").append(truncated).append('\n');
        doc.append("  },\n");
        doc.append("  \"initialGrid\": [\n");
        for (int i = 0; i < initialGrid.length; i++) {
            doc.append("    [");
            for (int j = 0; j < initialGrid[i].length; j++) {
                if (j > 0) {
                    doc.append(", ");
                }
                doc.append(initialGrid[i][j]);
            }
            doc.append(']');
            if (i + 1 < initialGrid.length) {
                doc.append(',');
            }
            doc.append('\n');
        }
        doc.append("  ],\n");
        doc.append("  \"events\": [\n    ");
        doc.append(buf);
        doc.append("\n  ]\n");
        doc.append("}\n");
        try {
            if (out.getParent() != null) {
                Files.createDirectories(out.getParent());
            }
            Files.writeString(out, doc, StandardCharsets.UTF_8);
            updateManifest(out.getParent());
            System.err.println("[gridviz] wrote " + eventCount + " events"
                    + (truncated ? " (truncated at gridviz.maxEvents=" + MAX_EVENTS + ")" : "")
                    + " -> " + out.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("[gridviz] failed to write trace: " + e.getMessage());
        }
    }

    private static Path resolveOut(String program) {
        if (OUT_PROP != null && !OUT_PROP.isBlank()) {
            return Paths.get(OUT_PROP).toAbsolutePath();
        }
        return findGridsDir().resolve(program + ".json");
    }

    private static Path findGridsDir() {
        Path dir = Paths.get("").toAbsolutePath();
        while (dir != null) {
            Path candidate = dir.resolve(Paths.get("visualizer", "grid-web", "public", "grids"));
            if (Files.isDirectory(candidate)) {
                return candidate;
            }
            if (Files.isDirectory(dir.resolve("visualizer"))
                    || Files.isDirectory(dir.resolve(Paths.get("visualizer", "grid-web")))) {
                try {
                    Files.createDirectories(candidate);
                    return candidate;
                } catch (IOException ignored) {
                    // keep walking
                }
            }
            dir = dir.getParent();
        }
        Path fallback = Paths.get("").toAbsolutePath().resolve("grids-out");
        try {
            Files.createDirectories(fallback);
        } catch (IOException ignored) {
            // write will surface the error
        }
        return fallback;
    }

    private static void updateManifest(Path gridsDir) {
        if (gridsDir == null || !Files.isDirectory(gridsDir)) {
            return;
        }
        List<String> names = new ArrayList<>();
        try (Stream<Path> stream = Files.list(gridsDir)) {
            stream.map(p -> p.getFileName().toString())
                    .filter(n -> n.endsWith(".json"))
                    .filter(n -> !n.equals("manifest.json"))
                    .sorted(Comparator.naturalOrder())
                    .forEach(names::add);
        } catch (IOException e) {
            System.err.println("[gridviz] could not list grids dir: " + e.getMessage());
            return;
        }
        StringBuilder sb = new StringBuilder("{\"grids\":[");
        for (int i = 0; i < names.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(quote(names.get(i)));
        }
        sb.append("]}\n");
        try {
            Files.writeString(gridsDir.resolve("manifest.json"), sb.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("[gridviz] could not update manifest: " + e.getMessage());
        }
    }

    private static String callerProgram() {
        for (StackTraceElement e : Thread.currentThread().getStackTrace()) {
            String cn = e.getClassName();
            if (cn.equals("java.lang.Thread") || cn.equals("graphs.GridTracer")) {
                continue;
            }
            int dot = cn.lastIndexOf('.');
            return dot >= 0 ? cn.substring(dot + 1) : cn;
        }
        return "grid";
    }

    private static String quote(String s) {
        StringBuilder sb = new StringBuilder(s.length() + 2);
        sb.append('"');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"' -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default -> {
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
                }
            }
        }
        sb.append('"');
        return sb.toString();
    }
}
