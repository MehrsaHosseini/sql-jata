package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.update.common.services.item.compiler;

import ir.mohaymen.querygenerator.domain.update.UpdateSet;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.column.OracleColumnReferenceCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.column.OracleColumnReferenceCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.value.OracleValueCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.value.OracleValueCompilerImpl;

import java.util.Objects;

public class OracleUpdateSetItemCompilerImpl implements OracleUpdateSetItemCompiler {

    private static final String ASSIGNMENT_OPERATOR = " = ";

    private final OracleColumnReferenceCompiler columnReferenceCompiler;
    private final OracleValueCompiler valueCompiler;

    public OracleUpdateSetItemCompilerImpl() {
        this(new OracleColumnReferenceCompilerImpl(), new OracleValueCompilerImpl());
    }

    public OracleUpdateSetItemCompilerImpl(OracleColumnReferenceCompiler columnReferenceCompiler,
                                           OracleValueCompiler valueCompiler) {
        this.columnReferenceCompiler = Objects.requireNonNull(columnReferenceCompiler, "column reference compiler must not be null");
        this.valueCompiler = Objects.requireNonNull(valueCompiler, "value compiler must not be null");
    }

    @Override
    public String compile(UpdateSet updateSet, OracleParameterBinder parameterBinder) {
        if (updateSet == null) {
            throw new IllegalArgumentException("update set item must not be null");
        }
        Objects.requireNonNull(parameterBinder, "parameter binder must not be null");

        return columnReferenceCompiler.compile(updateSet.column())
                + ASSIGNMENT_OPERATOR
                + valueCompiler.compileNullable(updateSet.value(), parameterBinder);
    }

}
