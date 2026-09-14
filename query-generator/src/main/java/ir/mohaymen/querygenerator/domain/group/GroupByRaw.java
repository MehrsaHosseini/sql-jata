package ir.mohaymen.querygenerator.domain.group;

import ir.mohaymen.querygenerator.domain.common.NamedParameters;

import java.util.Map;

public record GroupByRaw(String raw, Map<String, Object> parameters) implements GroupBy {

    public GroupByRaw(String raw) {
        this(raw, Map.of());
    }

    public GroupByRaw {
        parameters = NamedParameters.copy(parameters);
    }
}
