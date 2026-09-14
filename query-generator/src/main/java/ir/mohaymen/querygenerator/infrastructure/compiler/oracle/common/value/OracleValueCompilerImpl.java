package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.value;

import ir.mohaymen.querygenerator.domain.schema.column.Column;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.column.OracleColumnReferenceCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.column.OracleColumnReferenceCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

public class OracleValueCompilerImpl implements OracleValueCompiler {

    private final String LIST_PREFIX = "(";
    private final String LIST_SUFFIX = ")";
    private final String LIST_SEPARATOR = ", ";
    private final String RANGE_SEPARATOR = " AND ";
    private final String NULL_LITERAL = "NULL";
    private final int RANGE_SIZE = 2;

    private final OracleColumnReferenceCompiler columnReferenceCompiler;

    public OracleValueCompilerImpl() {
        this(new OracleColumnReferenceCompilerImpl());
    }

    public OracleValueCompilerImpl(OracleColumnReferenceCompiler columnReferenceCompiler) {
        this.columnReferenceCompiler = Objects.requireNonNull(columnReferenceCompiler, "column reference compiler must not be null");
    }

    @Override
    public String compile(Object value, OracleParameterBinder parameterBinder) {
        Objects.requireNonNull(parameterBinder, "parameter binder must not be null");

        Optional<List<Object>> elements = asElements(value);
        if (elements.isEmpty()) {
            return compileElement(value, parameterBinder);
        }

        List<Object> values = elements.get();
        if (values.isEmpty()) {
            throw new IllegalArgumentException("a condition value list must not be empty");
        }

        StringJoiner list = new StringJoiner(LIST_SEPARATOR, LIST_PREFIX, LIST_SUFFIX);
        for (Object element : values) {
            list.add(compileElement(element, parameterBinder));
        }
        return list.toString();
    }

    @Override
    public String compileRange(Object value, OracleParameterBinder parameterBinder) {
        Objects.requireNonNull(parameterBinder, "parameter binder must not be null");

        List<Object> values = asElements(value)
                .filter(elements -> elements.size() == RANGE_SIZE)
                .orElseThrow(() -> new IllegalArgumentException("a range condition needs exactly two values but got: " + value));

        return compileElement(values.getFirst(), parameterBinder)
                + RANGE_SEPARATOR
                + compileElement(values.getLast(), parameterBinder);
    }

    @Override
    public String compileNullable(Object value, OracleParameterBinder parameterBinder) {
        Objects.requireNonNull(parameterBinder, "parameter binder must not be null");

        Optional<List<Object>> elements = asElements(value);
        if (elements.isEmpty()) {
            return compileNullableElement(value, parameterBinder);
        }

        List<Object> values = elements.get();
        if (values.isEmpty()) {
            throw new IllegalArgumentException("a value list must not be empty");
        }

        StringJoiner list = new StringJoiner(LIST_SEPARATOR, LIST_PREFIX, LIST_SUFFIX);
        for (Object element : values) {
            list.add(compileNullableElement(element, parameterBinder));
        }
        return list.toString();
    }

    private String compileElement(Object value, OracleParameterBinder parameterBinder) {
        if (value == null) {
            throw new IllegalArgumentException("condition value must not be null, use a null condition instead");
        }
        if (value instanceof Column column) {
            return columnReferenceCompiler.compile(column);
        }
        return parameterBinder.bind(value);
    }

    private String compileNullableElement(Object value, OracleParameterBinder parameterBinder) {
        if (value == null) {
            return NULL_LITERAL;
        }
        return compileElement(value, parameterBinder);
    }

    private Optional<List<Object>> asElements(Object value) {
        if (value instanceof Collection<?> collection) {
            return Optional.of(new ArrayList<>(collection));
        }
        if (value != null && value.getClass().isArray()) {
            List<Object> elements = new ArrayList<>();
            for (int index = 0; index < Array.getLength(value); index++) {
                elements.add(Array.get(value, index));
            }
            return Optional.of(elements);
        }
        return Optional.empty();
    }

}
