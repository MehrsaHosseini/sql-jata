package ir.mohaymen.querygenerator.api.common.dto;

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
