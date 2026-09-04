import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Map;

/**
 * Runtime tracer injected next to instrumented solutions by the recviz CLI.
 *
 * <p>Records {@code enter}/{@code exit} events for recursive calls and writes them as a
 * single JSON document on JVM exit (shutdown hook). Self-contained: no dependencies
 * beyond the JDK, no package declaration, so it compiles alongside any solution file.
 *
 * <p>Configuration via system properties:
 * <ul>
 *   <li>{@code recviz.out} — output path for the trace (default {@code trace.json})</li>
 *   <li>{@code recviz.prog} — program name recorded in trace metadata</li>
 *   <li>{@code recviz.maxEvents} — event cap; tracing stops (program keeps running)
 *       once the cap is hit (default 200000)</li>
 * </ul>
 *
 * <p>Event schema (compact keys keep traces small):
 * <pre>
 * {"meta": {...}, "events": [
 *   {"t":"e","id":1,"p":null,"m":"helper","d":0,"s":0,"args":{"i":0,...}},
 *   {"t":"x","id":1,"s":9,"r":42}
 * ]}
 * </pre>
 * t: e=enter / x=exit, id: node id, p: parent id, m: method, d: depth, s: sequence.
 */
public final class Tracer {

    private static final String OUT = System.getProperty("recviz.out", "trace.json");
    private static final String PROG = System.getProperty("recviz.prog", "program");
    private static final long MAX_EVENTS =
            Long.parseLong(System.getProperty("recviz.maxEvents", "200000"));

    /** Max characters of serialized JSON produced for a single argument value. */
    private static final int VALUE_BUDGET = 600;
    private static final int MAX_ITEMS = 16;
    private static final int MAX_DEPTH = 4;
    private static final int MAX_STRING = 80;

    private static final ArrayDeque<Long> stack = new ArrayDeque<>();
    private static final StringBuilder buf = new StringBuilder(1 << 20);

    private static long nextId = 1;
    private static long seq = 0;
    private static long eventCount = 0;
    private static boolean truncated = false;
    private static boolean first = true;

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(Tracer::flush, "recviz-flush"));
    }

    private Tracer() {}

    /** Records a call entry. Returns the node id to pass to {@link #exit}, or 0 if not recording. */
    public static synchronized long enter(String method, String[] argNames, Object... argValues) {
        if (!beginEvent()) {
            return 0L;
        }
        long id = nextId++;
        Long parent = stack.peek();
        buf.append("{\"t\":\"e\",\"id\":").append(id);
        buf.append(",\"p\":").append(parent == null ? "null" : parent.toString());
        buf.append(",\"m\":").append(quote(method));
        buf.append(",\"d\":").append(stack.size());
        buf.append(",\"s\":").append(seq++);
        buf.append(",\"args\":{");
        for (int i = 0; i < argNames.length; i++) {
            if (i > 0) {
                buf.append(',');
            }
            buf.append(quote(argNames[i])).append(':');
            Object v = i < argValues.length ? argValues[i] : null;
            ser(v, 0, buf, new int[] {VALUE_BUDGET});
        }
        buf.append("}}");
        stack.push(id);
        return id;
    }

    /** Records a call exit with its return value. */
    public static synchronized void exit(long id, Object result) {
        if (id == 0L) {
            return;
        }
        popIfTop(id);
        if (!beginEvent()) {
            return;
        }
        buf.append("{\"t\":\"x\",\"id\":").append(id);
        buf.append(",\"s\":").append(seq++);
        buf.append(",\"r\":");
        ser(result, 0, buf, new int[] {VALUE_BUDGET});
        buf.append('}');
    }

    /** Records a call exit for {@code void} methods. */
    public static synchronized void exit(long id) {
        if (id == 0L) {
            return;
        }
        popIfTop(id);
        if (!beginEvent()) {
            return;
        }
        buf.append("{\"t\":\"x\",\"id\":").append(id);
        buf.append(",\"s\":").append(seq++);
        buf.append(",\"r\":null,\"v\":1}");
    }

    private static void popIfTop(long id) {
        if (!stack.isEmpty() && stack.peek() == id) {
            stack.pop();
        }
    }

    /** Reserves space for one event; returns false when tracing has been switched off. */
    private static boolean beginEvent() {
        if (truncated) {
            return false;
        }
        if (eventCount >= MAX_EVENTS) {
            truncated = true;
            return false;
        }
        if (!first) {
            buf.append(',');
        }
        first = false;
        eventCount++;
        return true;
    }

    private static void flush() {
        StringBuilder out = new StringBuilder(buf.length() + 256);
        out.append("{\"meta\":{");
        out.append("\"program\":").append(quote(PROG));
        out.append(",\"createdAt\":").append(quote(Instant.now().toString()));
        out.append(",\"events\":").append(eventCount);
        out.append(",\"maxEvents\":").append(MAX_EVENTS);
        out.append(",\"truncated\":").append(truncated);
        out.append("},\"events\":[");
        out.append(buf);
        out.append("]}");
        try {
            Path path = Paths.get(OUT).toAbsolutePath();
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.writeString(path, out, StandardCharsets.UTF_8);
            System.err.println("[recviz] wrote " + eventCount + " events"
                    + (truncated ? " (truncated at recviz.maxEvents=" + MAX_EVENTS + ")" : "")
                    + " -> " + path);
        } catch (IOException e) {
            System.err.println("[recviz] failed to write trace: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------------
    // Value serialization (hand-rolled JSON, budget-capped)
    // ------------------------------------------------------------------

    private static void ser(Object v, int depth, StringBuilder sb, int[] budget) {
        if (budget[0] <= 0) {
            sb.append("\"…\"");
            return;
        }
        if (v == null) {
            sb.append("null");
            return;
        }
        if (v instanceof String s) {
            String t = trunc(s, Math.min(MAX_STRING, Math.max(8, budget[0] - 2)));
            budget[0] -= t.length() + 2;
            sb.append(quote(t));
            return;
        }
        if (v instanceof Character c) {
            sb.append(quote(c.toString()));
            return;
        }
        if (v instanceof Number || v instanceof Boolean) {
            sb.append(v);
            return;
        }
        if (depth >= MAX_DEPTH) {
            sb.append("\"…\"");
            return;
        }
        Class<?> cl = v.getClass();
        if (cl.isArray()) {
            int n = Array.getLength(v);
            sb.append('[');
            int lim = Math.min(n, MAX_ITEMS);
            for (int i = 0; i < lim; i++) {
                if (i > 0) {
                    sb.append(',');
                }
                int before = sb.length();
                ser(Array.get(v, i), depth + 1, sb, budget);
                budget[0] -= (sb.length() - before);
                if (budget[0] <= 0) {
                    sb.append(",\"…\"");
                    sb.append(']');
                    return;
                }
            }
            if (n > lim) {
                sb.append(",\"…(").append(n).append(" items)\"");
            }
            sb.append(']');
            return;
        }
        if (v instanceof Collection<?> coll) {
            sb.append('[');
            int i = 0;
            for (Object item : coll) {
                if (i >= MAX_ITEMS) {
                    sb.append(",\"…(").append(coll.size()).append(" items)\"");
                    break;
                }
                if (i > 0) {
                    sb.append(',');
                }
                int before = sb.length();
                ser(item, depth + 1, sb, budget);
                budget[0] -= (sb.length() - before);
                if (budget[0] <= 0) {
                    sb.append(",\"…\"");
                    break;
                }
                i++;
            }
            sb.append(']');
            return;
        }
        if (v instanceof Map<?, ?> map) {
            sb.append('{');
            int i = 0;
            for (Map.Entry<?, ?> e : map.entrySet()) {
                if (i >= MAX_ITEMS) {
                    sb.append(",\"…\":\"(").append(map.size()).append(" entries)\"");
                    break;
                }
                if (i > 0) {
                    sb.append(',');
                }
                sb.append(quote(trunc(String.valueOf(e.getKey()), 24))).append(':');
                int before = sb.length();
                ser(e.getValue(), depth + 1, sb, budget);
                budget[0] -= (sb.length() - before);
                if (budget[0] <= 0) {
                    sb.append("\"…\"");
                    break;
                }
                i++;
            }
            sb.append('}');
            return;
        }
        sb.append(quote(trunc(String.valueOf(v), 120)));
    }



    private static String trunc(String s, int max) {
        return s.length() <= max ? s : s.substring(0, max) + "…";
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
                case '\b' -> sb.append("\\b");
                case '\f' -> sb.append("\\f");
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
