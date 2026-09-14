package ir.mohaymen.querygenerator.api.rest.common.dto;

import ir.mohaymen.querygenerator.application.query.generate.common.model.Query;

import java.util.Map;

public record QueryResponse(String query, Map<String, Object> parameters) {

    public static QueryResponse from(Query query) {
        return new QueryResponse(query.query(), query.parameters());
    }
}
