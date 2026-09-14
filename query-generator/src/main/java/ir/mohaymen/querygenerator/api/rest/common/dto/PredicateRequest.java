package ir.mohaymen.querygenerator.api.rest.common.dto;

import ir.mohaymen.querygenerator.domain.schema.enumeration.DatePart;

import java.util.List;
import java.util.Map;

public record PredicateRequest(
        String op,
        String column,
        Object value,
        List<Object> values,
        Object start,
        Object end,
        List<PredicateRequest> conditions,
        String table,
        String alias,
        PredicateRequest where,
        DatePart datePart,
        String operation,
        String sql,
        Map<String, Object> parameters
) {

    public static PredicateRequest eq(String column, Object value) {
        return new PredicateRequest("eq", column, value, null, null, null, null, null, null, null, null, null, null, null);
    }

    public static PredicateRequest isNull(String column) {
        return new PredicateRequest("isNull", column, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public static PredicateRequest and(PredicateRequest... conditions) {
        return new PredicateRequest("and", null, null, null, null, null, List.of(conditions), null, null, null, null, null, null, null);
    }

    public static PredicateRequest or(PredicateRequest... conditions) {
        return new PredicateRequest("or", null, null, null, null, null, List.of(conditions), null, null, null, null, null, null, null);
    }

    public static PredicateRequest raw(String sql, Map<String, Object> parameters) {
        return new PredicateRequest("raw", null, null, null, null, null, null, null, null, null, null, null, sql, parameters);
    }
}
