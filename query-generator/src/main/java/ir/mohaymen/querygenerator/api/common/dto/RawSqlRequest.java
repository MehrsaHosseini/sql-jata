package ir.mohaymen.querygenerator.api.common.dto;

import java.util.Map;

public record RawSqlRequest(String sql, Map<String, Object> parameters) {

    public RawSqlRequest(String sql) {
        this(sql, Map.of());
    }
}
