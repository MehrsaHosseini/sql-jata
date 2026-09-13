package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.target.compiler;

import ir.mohaymen.querygenerator.domain.insert.InsertSingleRow;
import ir.mohaymen.querygenerator.domain.schema.column.Column;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.columns.compiler.OracleInsertColumnsCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.columns.compiler.OracleInsertColumnsCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.values.compiler.OracleInsertValuesCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.values.compiler.OracleInsertValuesCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.table.OracleTableReferenceCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.table.OracleTableReferenceCompilerImpl;

import java.util.List;
import java.util.Objects;

public class OracleInsertTargetCompilerImpl implements OracleInsertTargetCompiler {

    private static final String VALUES_KEYWORD = " VALUES ";

    private final OracleTableReferenceCompiler tableReferenceCompiler;
    private final OracleInsertColumnsCompiler insertColumnsCompiler;
    private final OracleInsertValuesCompiler insertValuesCompiler;

    public OracleInsertTargetCompilerImpl() {
        this(new OracleTableReferenceCompilerImpl(),
                new OracleInsertColumnsCompilerImpl(),
                new OracleInsertValuesCompilerImpl());
    }

    public OracleInsertTargetCompilerImpl(OracleTableReferenceCompiler tableReferenceCompiler,
                                          OracleInsertColumnsCompiler insertColumnsCompiler,
                                          OracleInsertValuesCompiler insertValuesCompiler) {
        this.tableReferenceCompiler = Objects.requireNonNull(tableReferenceCompiler, "table reference compiler must not be null");
        this.insertColumnsCompiler = Objects.requireNonNull(insertColumnsCompiler, "insert columns compiler must not be null");
        this.insertValuesCompiler = Objects.requireNonNull(insertValuesCompiler, "insert values compiler must not be null");
    }

    @Override
    public String compile(InsertSingleRow insert, OracleParameterBinder parameterBinder) {
        if (insert == null) {
            throw new IllegalArgumentException("insert target must not be null");
        }
        Objects.requireNonNull(parameterBinder, "parameter binder must not be null");

        List<Column> columnList = insert.columnList();
        List<Object> values = insert.values();
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("insert values must not be null or empty");
        }
        requireMatchingSize(columnList, values);

        return tableReferenceCompiler.compile(insert.table())
                + insertColumnsCompiler.compile(columnList)
                + VALUES_KEYWORD
                + insertValuesCompiler.compile(values, parameterBinder);
    }

    private static void requireMatchingSize(List<Column> columnList, List<Object> values) {
        if (columnList == null || columnList.isEmpty()) {
            return;
        }
        if (columnList.size() != values.size()) {
            throw new IllegalArgumentException(
                    "insert columns and values must have the same size but got columns: "
                            + columnList.size() + " values: " + values.size());
        }
    }

}
