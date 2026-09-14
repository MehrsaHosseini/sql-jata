package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.columns.compiler;

import ir.mohaymen.querygenerator.domain.schema.column.Column;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.column.OracleColumnReferenceCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.column.OracleColumnReferenceCompilerImpl;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class OracleInsertColumnsCompilerImpl implements OracleInsertColumnsCompiler {

    private static final String COLUMN_SEPARATOR = ", ";
    private static final String COLUMN_LIST_PREFIX = " (";
    private static final String COLUMN_LIST_SUFFIX = ")";
    private static final String EMPTY_COLUMNS = "";

    private final OracleColumnReferenceCompiler columnReferenceCompiler;

    public OracleInsertColumnsCompilerImpl() {
        this(new OracleColumnReferenceCompilerImpl());
    }

    public OracleInsertColumnsCompilerImpl(OracleColumnReferenceCompiler columnReferenceCompiler) {
        this.columnReferenceCompiler = Objects.requireNonNull(columnReferenceCompiler, "column reference compiler must not be null");
    }

    @Override
    public String compile(List<Column> columnList) {
        if (columnList == null || columnList.isEmpty()) {
            return EMPTY_COLUMNS;
        }

        StringJoiner columns = new StringJoiner(COLUMN_SEPARATOR, COLUMN_LIST_PREFIX, COLUMN_LIST_SUFFIX);
        for (Column column : columnList) {
            if (column == null) {
                throw new IllegalArgumentException("insert column must not be null");
            }
            columns.add(columnReferenceCompiler.compile(column));
        }
        return columns.toString();
    }

}
