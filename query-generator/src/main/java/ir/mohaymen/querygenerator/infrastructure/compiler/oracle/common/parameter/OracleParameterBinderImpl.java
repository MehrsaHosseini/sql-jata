package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter;

import java.util.List;
import java.util.Objects;

/**
 * Holds the parameter list of a single query, so an instance must not be shared between queries.
 */
public class OracleParameterBinderImpl implements OracleParameterBinder {

    private static final String PLACEHOLDER_PREFIX = ":";

    private final List<Object> parameters;

    public OracleParameterBinderImpl(List<Object> parameters) {
        this.parameters = Objects.requireNonNull(parameters, "parameters must not be null");
    }

    @Override
    public String bind(Object value) {
        parameters.add(value);
        return PLACEHOLDER_PREFIX + parameters.size();
    }

}
