package ir.mohaymen.querygenerator.api.rest.dto;

import ir.mohaymen.querygenerator.domain.parameter.ParameterMode;

import java.util.Map;

public record DeleteQueryRequest(
        String table,
        String alias,
        PredicateRequest where,
        ParameterMode parameterMode,
        Map<String, Object> bind
) {
}
