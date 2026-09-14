package ir.mohaymen.querygenerator.domain.where;

import ir.mohaymen.querygenerator.domain.common.NamedParameters;

import java.util.Map;

public record WhereRaw(String raw, Map<String, Object> parameters) implements Where {

    public WhereRaw(String raw) {
        this(raw, Map.of());
    }

    public WhereRaw {
        parameters = NamedParameters.copy(parameters);
    }
}
