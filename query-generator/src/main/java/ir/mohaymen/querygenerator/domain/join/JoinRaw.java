package ir.mohaymen.querygenerator.domain.join;

import ir.mohaymen.querygenerator.domain.common.NamedParameters;

import java.util.Map;

public record JoinRaw(String raw, Map<String, Object> parameters) implements Join {

    public JoinRaw(String raw) {
        this(raw, Map.of());
    }

    public JoinRaw {
        parameters = NamedParameters.copy(parameters);
    }
}
