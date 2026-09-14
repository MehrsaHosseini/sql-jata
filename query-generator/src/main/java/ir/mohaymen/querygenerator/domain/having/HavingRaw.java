package ir.mohaymen.querygenerator.domain.having;

import ir.mohaymen.querygenerator.domain.common.NamedParameters;

import java.util.Map;

/**
 * Filters an aggregate expression such as {@code COUNT(*) > 50} in place, written by the caller.
 */
public record HavingRaw(String raw, Map<String, Object> parameters) implements Having {

    public HavingRaw(String raw) {
        this(raw, Map.of());
    }

    public HavingRaw {
        parameters = NamedParameters.copy(parameters);
    }
}
