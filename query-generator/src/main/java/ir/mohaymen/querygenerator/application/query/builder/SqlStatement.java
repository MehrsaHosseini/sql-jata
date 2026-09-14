package ir.mohaymen.querygenerator.application.query.builder;

import ir.mohaymen.querygenerator.application.query.generate.QueryGenerator;
import ir.mohaymen.querygenerator.application.query.generate.QueryGeneratorRequest;
import ir.mohaymen.querygenerator.application.query.generate.common.model.Query;
import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.domain.common.NamedParameters;
import ir.mohaymen.querygenerator.domain.parameter.ParameterMode;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public abstract class SqlStatement<T extends SqlStatement<T>> {

    private ParameterMode parameterMode = ParameterMode.NAMED;
    private final Map<String, Object> namedParameters = new LinkedHashMap<>();

    public abstract QueryGeneratorRequest toRequest();

    public Query generate() {
        return QueryGenerator.oracle().generate(toRequest());
    }

    public Query generate(QueryGenerator generator) {
        return Objects.requireNonNull(generator, "query generator must not be null").generate(toRequest());
    }

    public T named() {
        this.parameterMode = ParameterMode.NAMED;
        return self();
    }

    public T inline() {
        this.parameterMode = ParameterMode.INLINE;
        return self();
    }

    public T bind(String name, Object value) {
        NamedParameters.put(namedParameters, name, value);
        return self();
    }

    public T bind(Map<String, Object> parameters) {
        NamedParameters.putAll(namedParameters, parameters);
        return self();
    }

    protected QueryContext applyBindings(QueryContext context) {
        return context.withParameterMode(parameterMode).bind(namedParameters);
    }

    @SuppressWarnings("unchecked")
    private T self() {
        return (T) this;
    }
}
