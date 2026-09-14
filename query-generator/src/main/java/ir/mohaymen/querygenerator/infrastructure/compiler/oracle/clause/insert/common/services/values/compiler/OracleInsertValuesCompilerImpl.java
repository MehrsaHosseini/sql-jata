package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.values.compiler;

import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.value.OracleValueCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.value.OracleValueCompilerImpl;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class OracleInsertValuesCompilerImpl implements OracleInsertValuesCompiler {

    private static final String VALUE_SEPARATOR = ", ";
    private static final String VALUE_LIST_PREFIX = "(";
    private static final String VALUE_LIST_SUFFIX = ")";

    private final OracleValueCompiler valueCompiler;

    public OracleInsertValuesCompilerImpl() {
        this(new OracleValueCompilerImpl());
    }

    public OracleInsertValuesCompilerImpl(OracleValueCompiler valueCompiler) {
        this.valueCompiler = Objects.requireNonNull(valueCompiler, "value compiler must not be null");
    }

    @Override
    public String compile(List<Object> values, OracleParameterBinder parameterBinder) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("insert values must not be null or empty");
        }
        Objects.requireNonNull(parameterBinder, "parameter binder must not be null");

        StringJoiner compiled = new StringJoiner(VALUE_SEPARATOR, VALUE_LIST_PREFIX, VALUE_LIST_SUFFIX);
        for (Object value : values) {
            compiled.add(valueCompiler.compileNullable(value, parameterBinder));
        }
        return compiled.toString();
    }

}
