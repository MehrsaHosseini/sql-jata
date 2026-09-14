package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter;

import ir.mohaymen.querygenerator.domain.common.NamedParameters;
import ir.mohaymen.querygenerator.domain.parameter.ParameterMode;

import java.util.Map;
import java.util.Objects;

/**
 * Holds the named parameter map of a single query, so an instance must not be shared between queries.
 */
public class OracleParameterBinderImpl implements OracleParameterBinder {

    private static final String PLACEHOLDER_PREFIX = ":";
    private static final String GENERATED_PREFIX = "p";

    private final Map<String, Object> parameters;
    private final ParameterMode parameterMode;
    private final OracleSqlLiteralRenderer literalRenderer;
    private int nextGeneratedIndex = 1;

    public OracleParameterBinderImpl(Map<String, Object> parameters) {
        this(parameters, ParameterMode.NAMED);
    }

    public OracleParameterBinderImpl(Map<String, Object> parameters, ParameterMode parameterMode) {
        this.parameters = Objects.requireNonNull(parameters, "parameters must not be null");
        this.parameterMode = parameterMode == null ? ParameterMode.NAMED : parameterMode;
        this.literalRenderer = new OracleSqlLiteralRenderer();
    }

    @Override
    public String bind(Object value) {
        if (parameterMode == ParameterMode.INLINE) {
            return literalRenderer.render(value);
        }
        String name = nextGeneratedName();
        parameters.put(name, value);
        return PLACEHOLDER_PREFIX + name;
    }

    @Override
    public void put(String name, Object value) {
        NamedParameters.put(parameters, name, value);
    }

    @Override
    public void putAll(Map<String, Object> namedParameters) {
        NamedParameters.putAll(parameters, namedParameters);
    }

    @Override
    public String renderSql(String sql) {
        if (parameterMode != ParameterMode.INLINE) {
            return sql;
        }
        return literalRenderer.substitute(sql, parameters);
    }

    private String nextGeneratedName() {
        String name;
        do {
            name = GENERATED_PREFIX + nextGeneratedIndex++;
        } while (parameters.containsKey(name));
        return name;
    }
}
