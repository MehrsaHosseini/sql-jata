package ir.mohaymen.querygenerator.api.common.dto;

import ir.mohaymen.querygenerator.domain.parameter.ParameterMode;

import java.util.Map;

public record UpdateQueryRequest(
        String table,
        String alias,
        Map<String, Object> set,
        PredicateRequest where,
        ParameterMode parameterMode,
        Map<String, Object> bind
) {
}
