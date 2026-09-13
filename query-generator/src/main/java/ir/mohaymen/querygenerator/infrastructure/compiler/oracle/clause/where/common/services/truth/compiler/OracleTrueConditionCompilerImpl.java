package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.truth.compiler;

import ir.mohaymen.querygenerator.domain.where.WhereTrue;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.column.OracleColumnReferenceCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.column.OracleColumnReferenceCompilerImpl;

import java.util.Objects;

public class OracleTrueConditionCompilerImpl implements OracleTrueConditionCompiler {

    private static final String IS_TRUE = " = 1";
    private static final String IS_FALSE = " = 0";

    private final OracleColumnReferenceCompiler columnReferenceCompiler;

    public OracleTrueConditionCompilerImpl() {
        this(new OracleColumnReferenceCompilerImpl());
    }

    public OracleTrueConditionCompilerImpl(OracleColumnReferenceCompiler columnReferenceCompiler) {
        this.columnReferenceCompiler = Objects.requireNonNull(columnReferenceCompiler, "column reference compiler must not be null");
    }

    @Override
    public String compile(WhereTrue whereTrue) {
        if (whereTrue == null) {
            throw new IllegalArgumentException("true condition must not be null");
        }

        String column = columnReferenceCompiler.compile(whereTrue.column());
        return column + (Boolean.TRUE.equals(whereTrue.negative()) ? IS_FALSE : IS_TRUE);
    }

}
