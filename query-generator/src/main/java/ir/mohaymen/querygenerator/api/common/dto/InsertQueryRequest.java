package ir.mohaymen.querygenerator.api.common.dto;

import ir.mohaymen.querygenerator.domain.parameter.ParameterMode;

import java.util.List;
import java.util.Map;

public record InsertQueryRequest(
        String table,
        String alias,
        List<String> columns,
        List<Object> values,
        ParameterMode parameterMode,
        Map<String, Object> bind
) {
}
