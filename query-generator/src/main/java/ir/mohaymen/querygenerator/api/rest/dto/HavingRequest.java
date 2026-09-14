package ir.mohaymen.querygenerator.api.rest.dto;

import java.util.List;
import java.util.Map;

public record HavingRequest(
        String op,
        String alias,
        String operation,
        Object value,
        String sql,
        Map<String, Object> parameters,
        List<HavingRequest> conditions
) {

    public static HavingRequest alias(String alias, String operation, Object value) {
        return new HavingRequest("alias", alias, operation, value, null, null, null);
    }

    public static HavingRequest raw(String sql, Map<String, Object> parameters) {
        return new HavingRequest("raw", null, null, null, sql, parameters, null);
    }

    public static HavingRequest and(HavingRequest... conditions) {
        return new HavingRequest("and", null, null, null, null, null, List.of(conditions));
    }
}
