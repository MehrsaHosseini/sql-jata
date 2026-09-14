package ir.mohaymen.querygenerator.domain.common;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Named bindings for {@code :name} placeholders, including caller-supplied raw SQL parameters.
 */
public final class NamedParameters {

    private NamedParameters() {
    }

    public static Map<String, Object> copy(Map<String, Object> parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return Map.of();
        }
        return Collections.unmodifiableMap(new LinkedHashMap<>(parameters));
    }

    public static String requireName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("parameter name must not be null or blank");
        }
        String normalized = name.trim();
        char prefix = normalized.charAt(0);
        if (prefix == ':' || prefix == '@') {
            normalized = normalized.substring(1);
        }
        if (normalized.isBlank() || !isValid(normalized)) {
            throw new IllegalArgumentException("parameter name is not valid for named binding: " + name);
        }
        return normalized;
    }

    public static void put(Map<String, Object> target, String name, Object value) {
        Objects.requireNonNull(target, "parameters must not be null");
        String key = requireName(name);
        if (target.containsKey(key) && !Objects.equals(target.get(key), value)) {
            throw new IllegalArgumentException("parameter already bound with a different value: " + key);
        }
        target.put(key, value);
    }

    public static void putAll(Map<String, Object> target, Map<String, Object> source) {
        if (source == null || source.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : source.entrySet()) {
            put(target, entry.getKey(), entry.getValue());
        }
    }

    private static boolean isValid(String name) {
        char first = name.charAt(0);
        if (!(Character.isLetter(first) || first == '_')) {
            return false;
        }
        for (int index = 1; index < name.length(); index++) {
            char current = name.charAt(index);
            if (!(Character.isLetterOrDigit(current) || current == '_')) {
                return false;
            }
        }
        return true;
    }
}
