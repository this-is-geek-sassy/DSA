## Plan: Java debug logging with variable names

Java cannot recover local variable names from a plain varargs call, so the names must be carried explicitly. The wrapper approach is the right fit here because it keeps the call site readable while still preserving the labels you typed.

1. Define a tiny wrapper class, for example `DebugValue`, with two fields: `String name` and `Object value`.
2. Add a factory method such as `DebugValue.of(String name, Object value)` so the call site stays compact.
3. Replace the current helper signature with a varargs method that accepts `DebugValue... values` instead of raw primitives.
4. In the helper, print each item as `name = value`, not just the value.
5. Add a small formatter for arrays so the helper can handle `int[]`, `long[]`, `Object[]`, and nested arrays without unreadable output.
6. Route debug output to `System.err` or gate it behind a `DEBUG` flag, so regular problem output on `System.out` stays clean.
7. Update call sites to pass labeled wrappers, for example `log(DebugValue.of("low", low), DebugValue.of("high", high))`.
8. Keep the helper reusable across other DSA files by placing it in a small shared utility or by pasting the same wrapper pattern where needed.
9. Test the helper with primitives, strings, arrays, and several arguments in one call.

## Recommended shape

```java
static final class DebugValue {
   final String name;
   final Object value;

   DebugValue(String name, Object value) {
      this.name = name;
      this.value = value;
   }

   static DebugValue of(String name, Object value) {
      return new DebugValue(name, value);
   }
}
```

## What this gives you

You still need to write the names yourself, but you only do that once per value and the helper preserves them all the way to the terminal. That is the practical limit in Java without relying on brittle reflection or compiler-specific tricks.
