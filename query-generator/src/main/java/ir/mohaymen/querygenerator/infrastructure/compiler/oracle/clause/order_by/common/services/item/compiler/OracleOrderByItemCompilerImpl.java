package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.order_by.common.services.item.compiler;

import ir.mohaymen.querygenerator.domain.order.OrderByItem;
import ir.mohaymen.querygenerator.domain.schema.enumeration.SortDirection;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.column.OracleColumnReferenceCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.column.OracleColumnReferenceCompilerImpl;

import java.util.Objects;

public class OracleOrderByItemCompilerImpl implements OracleOrderByItemCompiler {

    private static final String DIRECTION_SEPARATOR = " ";

    private final OracleColumnReferenceCompiler columnReferenceCompiler;

    public OracleOrderByItemCompilerImpl() {
        this(new OracleColumnReferenceCompilerImpl());
    }

    public OracleOrderByItemCompilerImpl(OracleColumnReferenceCompiler columnReferenceCompiler) {
        this.columnReferenceCompiler = Objects.requireNonNull(columnReferenceCompiler, "column reference compiler must not be null");
    }

    @Override
    public String compile(OrderByItem orderByItem) {
        if (orderByItem == null) {
            throw new IllegalArgumentException("order by item must not be null");
        }

        String column = columnReferenceCompiler.compile(orderByItem.column());
        SortDirection direction = orderByItem.direction();
        if (direction == null) {
            return column;
        }
        return column + DIRECTION_SEPARATOR + direction.name();
    }

}
