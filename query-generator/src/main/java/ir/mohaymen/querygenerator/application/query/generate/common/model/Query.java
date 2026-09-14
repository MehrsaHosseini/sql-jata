package ir.mohaymen.querygenerator.application.query.generate.common.model;

import ir.mohaymen.querygenerator.domain.common.NamedParameters;

import java.util.Map;

public record Query(String query, Map<String, Object> parameters) {

    public Query {
        parameters = NamedParameters.copy(parameters);
    }

    public Query(String query) {
        this(query, Map.of());
    }
}
