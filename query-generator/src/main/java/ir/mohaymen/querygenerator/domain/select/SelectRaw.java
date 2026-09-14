package ir.mohaymen.querygenerator.domain.select;

import ir.mohaymen.querygenerator.domain.common.NamedParameters;

import java.util.Map;

public record SelectRaw(String raw, Map<String, Object> parameters) implements Select {

    public SelectRaw(String raw) {
        this(raw, Map.of());
    }

    public SelectRaw {
        parameters = NamedParameters.copy(parameters);
    }
}
