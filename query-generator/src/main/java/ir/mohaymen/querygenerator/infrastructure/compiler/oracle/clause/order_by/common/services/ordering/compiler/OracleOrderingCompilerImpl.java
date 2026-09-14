package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.order_by.common.services.ordering.compiler;

import ir.mohaymen.querygenerator.domain.order.OrderBy;
import ir.mohaymen.querygenerator.domain.order.OrderByColumn;
import ir.mohaymen.querygenerator.domain.order.OrderByItem;
import ir.mohaymen.querygenerator.domain.order.OrderByRaw;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.order_by.common.services.item.compiler.OracleOrderByItemCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.order_by.common.services.item.compiler.OracleOrderByItemCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class OracleOrderingCompilerImpl implements OracleOrderingCompiler {

    private static final String ITEM_SEPARATOR = ", ";

    private final OracleOrderByItemCompiler orderByItemCompiler;

    public OracleOrderingCompilerImpl() {
        this(new OracleOrderByItemCompilerImpl());
    }

    public OracleOrderingCompilerImpl(OracleOrderByItemCompiler orderByItemCompiler) {
        this.orderByItemCompiler = Objects.requireNonNull(orderByItemCompiler, "order by item compiler must not be null");
    }

    @Override
    public String compile(OrderBy orderBy, OracleParameterBinder parameterBinder) {
        Objects.requireNonNull(parameterBinder, "parameter binder must not be null");
        return switch (orderBy) {
            case null -> throw new IllegalArgumentException("order by must not be null");
            case OrderByRaw orderByRaw -> compileRaw(orderByRaw, parameterBinder);
            case OrderByColumn orderByColumn -> compileColumns(orderByColumn.columnList());
        };
    }

    private static String compileRaw(OrderByRaw orderByRaw, OracleParameterBinder parameterBinder) {
        String raw = orderByRaw.raw();
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("raw order by expression must not be null or blank");
        }
        parameterBinder.putAll(orderByRaw.parameters());
        return parameterBinder.renderSql(raw.trim());
    }

    private String compileColumns(List<OrderByItem> columnList) {
        if (columnList == null || columnList.isEmpty()) {
            throw new IllegalArgumentException("order by columns must not be null or empty");
        }

        StringJoiner items = new StringJoiner(ITEM_SEPARATOR);
        for (OrderByItem orderByItem : columnList) {
            items.add(orderByItemCompiler.compile(orderByItem));
        }
        return items.toString();
    }

}
