package ir.mohaymen.querygenerator.api.rest.common.dto;

import ir.mohaymen.querygenerator.domain.parameter.ParameterMode;

import java.util.List;
import java.util.Map;

public record InsertAllQueryRequest(
        List<InsertQueryRequest> rows,
        ParameterMode parameterMode,
        Map<String, Object> bind
) {
}
