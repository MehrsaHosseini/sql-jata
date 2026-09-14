package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.nullable.compiler;

import ir.mohaymen.querygenerator.domain.where.WhereNull;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.column.OracleColumnReferenceCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.column.OracleColumnReferenceCompilerImpl;

import java.util.Objects;

public class OracleNullConditionCompilerImpl implements OracleNullConditionCompiler {

    private static final String IS_NULL = " IS NULL";
    private static final String IS_NOT_NULL = " IS NOT NULL";

    private final OracleColumnReferenceCompiler columnReferenceCompiler;

    public OracleNullConditionCompilerImpl() {
        this(new OracleColumnReferenceCompilerImpl());
    }

    public OracleNullConditionCompilerImpl(OracleColumnReferenceCompiler columnReferenceCompiler) {
        this.columnReferenceCompiler = Objects.requireNonNull(columnReferenceCompiler, "column reference compiler must not be null");
    }

    @Override
    public String compile(WhereNull whereNull) {
        if (whereNull == null) {
            throw new IllegalArgumentException("null condition must not be null");
        }

        String column = columnReferenceCompiler.compile(whereNull.column());
        return column + (Boolean.FALSE.equals(whereNull.isNull()) ? IS_NOT_NULL : IS_NULL);
    }

}
