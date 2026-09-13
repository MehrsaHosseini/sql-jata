package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.basic.compiler;

import ir.mohaymen.querygenerator.domain.where.BasicWhere;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.value.compiler.OracleValueCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.value.compiler.OracleValueCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.column.OracleColumnReferenceCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.column.OracleColumnReferenceCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class OracleBasicConditionCompilerImpl implements OracleBasicConditionCompiler {

    private static final String OPERAND_SEPARATOR = " ";
    private static final String WHITESPACE_PATTERN = "\\s+";
    private static final Set<String> RANGE_OPERATIONS = Set.of("BETWEEN", "NOT BETWEEN");

    private final OracleColumnReferenceCompiler columnReferenceCompiler;
    private final OracleValueCompiler valueCompiler;

    public OracleBasicConditionCompilerImpl() {
        this(new OracleColumnReferenceCompilerImpl(), new OracleValueCompilerImpl());
    }

    public OracleBasicConditionCompilerImpl(OracleColumnReferenceCompiler columnReferenceCompiler,
                                            OracleValueCompiler valueCompiler) {
        this.columnReferenceCompiler = Objects.requireNonNull(columnReferenceCompiler, "column reference compiler must not be null");
        this.valueCompiler = Objects.requireNonNull(valueCompiler, "value compiler must not be null");
    }

    @Override
    public String compile(BasicWhere basicWhere, OracleParameterBinder parameterBinder) {
        if (basicWhere == null) {
            throw new IllegalArgumentException("basic condition must not be null");
        }

        String operation = requireOperation(basicWhere.operation());
        String column = columnReferenceCompiler.compile(basicWhere.column());
        String value = isRange(operation)
                ? valueCompiler.compileRange(basicWhere.value(), parameterBinder)
                : valueCompiler.compile(basicWhere.value(), parameterBinder);

        return column + OPERAND_SEPARATOR + operation + OPERAND_SEPARATOR + value;
    }

    private static String requireOperation(String operation) {
        if (operation == null || operation.isBlank()) {
            throw new IllegalArgumentException("condition operation must not be null or blank");
        }
        return operation.trim();
    }

    private static boolean isRange(String operation) {
        return RANGE_OPERATIONS.contains(operation.toUpperCase(Locale.ROOT).replaceAll(WHITESPACE_PATTERN, OPERAND_SEPARATOR));
    }

}
