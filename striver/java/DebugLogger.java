import java.lang.reflect.Array;
import java.util.Arrays;

public final class DebugLogger {

    private DebugLogger() {
    }

    public static final class Arg {
        private final String name;
        private final Object value;

        private Arg(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public static Arg of(String name, Object value) {
            return new Arg(name, value);
        }
    }

    public static void log(Arg... args) {
        if (args == null || args.length == 0) {
            return;
        }
        StringBuilder message = new StringBuilder();
        for (Arg arg : args) {
            if (message.length() > 0) {
                message.append(";\t");
            }
            message.append(arg.name).append(" : ").append(formatValue(arg.value));
        }
        System.err.println(message);
    }

    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        }
        Class<?> valueType = value.getClass();
        if (!valueType.isArray()) {
            return String.valueOf(value);
        }
        if (value instanceof Object[] objectArray) {
            return Arrays.deepToString(objectArray);
        }
        int length = Array.getLength(value);
        StringBuilder arrayText = new StringBuilder("[");
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                arrayText.append(", ");
            }
            arrayText.append(Array.get(value, i));
        }
        return arrayText.append(']').toString();
    }
}
