package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.group_by.common.services.grouping.compiler;

import ir.mohaymen.querygenerator.domain.group.GroupBy;
import ir.mohaymen.querygenerator.domain.group.GroupByColumn;
import ir.mohaymen.querygenerator.domain.group.GroupByRaw;
import ir.mohaymen.querygenerator.domain.schema.column.Column;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.group_by.common.services.item.compiler.OracleGroupByItemCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.group_by.common.services.item.compiler.OracleGroupByItemCompilerImpl;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class OracleGroupingCompilerImpl implements OracleGroupingCompiler {

    private static final String ITEM_SEPARATOR = ", ";

    private final OracleGroupByItemCompiler groupByItemCompiler;

    public OracleGroupingCompilerImpl() {
        this(new OracleGroupByItemCompilerImpl());
    }

    public OracleGroupingCompilerImpl(OracleGroupByItemCompiler groupByItemCompiler) {
        this.groupByItemCompiler = Objects.requireNonNull(groupByItemCompiler, "group by item compiler must not be null");
    }

    @Override
    public String compile(GroupBy groupBy) {
        return switch (groupBy) {
            case null -> throw new IllegalArgumentException("group by must not be null");
            case GroupByRaw groupByRaw -> compileRaw(groupByRaw.raw());
            case GroupByColumn groupByColumn -> compileColumns(groupByColumn.columnList());
        };
    }

    private static String compileRaw(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("raw group by expression must not be null or blank");
        }
        return raw.trim();
    }

    private String compileColumns(List<Column> columnList) {
        if (columnList == null || columnList.isEmpty()) {
            throw new IllegalArgumentException("group by columns must not be null or empty");
        }

        StringJoiner items = new StringJoiner(ITEM_SEPARATOR);
        for (Column column : columnList) {
            items.add(groupByItemCompiler.compile(column));
        }
        return items.toString();
    }

}
