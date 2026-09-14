package ir.mohaymen.querygenerator.domain.order;

import ir.mohaymen.querygenerator.domain.common.NamedParameters;

import java.util.Map;

public record OrderByRaw(String raw, Map<String, Object> parameters) implements OrderBy {

    public OrderByRaw(String raw) {
        this(raw, Map.of());
    }

    public OrderByRaw {
        parameters = NamedParameters.copy(parameters);
    }
}
