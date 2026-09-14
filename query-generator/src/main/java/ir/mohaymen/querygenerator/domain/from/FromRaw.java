package ir.mohaymen.querygenerator.domain.from;

import ir.mohaymen.querygenerator.domain.common.NamedParameters;

import java.util.Map;

public record FromRaw(String raw, Map<String, Object> parameters) implements From {

    public FromRaw(String raw) {
        this(raw, Map.of());
    }

    public FromRaw {
        parameters = NamedParameters.copy(parameters);
    }
}
