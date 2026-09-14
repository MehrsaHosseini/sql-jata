package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter;

import java.util.Map;

public interface OracleParameterBinder {

    String bind(Object value);

    void put(String name, Object value);

    void putAll(Map<String, Object> namedParameters);

    String renderSql(String sql);

    default String raw(String sql, Map<String, Object> namedParameters) {
        putAll(namedParameters);
        return renderSql(sql);
    }
}
