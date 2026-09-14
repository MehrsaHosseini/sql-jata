package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.group_by.common.services.item.compiler;

import ir.mohaymen.querygenerator.domain.schema.column.Column;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.column.OracleColumnReferenceCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.column.OracleColumnReferenceCompilerImpl;

import java.util.Objects;

public class OracleGroupByItemCompilerImpl implements OracleGroupByItemCompiler {

    private final OracleColumnReferenceCompiler columnReferenceCompiler;

    public OracleGroupByItemCompilerImpl() {
        this(new OracleColumnReferenceCompilerImpl());
    }

    public OracleGroupByItemCompilerImpl(OracleColumnReferenceCompiler columnReferenceCompiler) {
        this.columnReferenceCompiler = Objects.requireNonNull(columnReferenceCompiler, "column reference compiler must not be null");
    }

    @Override
    public String compile(Column column) {
        if (column == null) {
            throw new IllegalArgumentException("group by column must not be null");
        }
        return columnReferenceCompiler.compile(column);
    }

}
