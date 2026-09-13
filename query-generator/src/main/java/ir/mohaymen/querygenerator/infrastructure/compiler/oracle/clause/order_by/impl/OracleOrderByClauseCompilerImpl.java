package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.order_by.impl;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.domain.order.OrderBy;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.order_by.OracleOrderByClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.order_by.common.services.ordering.compiler.OracleOrderingCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.order_by.common.services.ordering.compiler.OracleOrderingCompilerImpl;

import java.util.Objects;

public class OracleOrderByClauseCompilerImpl implements OracleOrderByClauseCompiler {

    private static final String ORDER_BY_KEYWORD = "ORDER BY ";
    private static final String CLAUSE_SEPARATOR = " ";

    private final OracleOrderingCompiler orderingCompiler;

    public OracleOrderByClauseCompilerImpl() {
        this(new OracleOrderingCompilerImpl());
    }

    public OracleOrderByClauseCompilerImpl(OracleOrderingCompiler orderingCompiler) {
        this.orderingCompiler = Objects.requireNonNull(orderingCompiler, "ordering compiler must not be null");
    }

    @Override
    public QueryContext generateOrderByClause(QueryContext context) {
        Objects.requireNonNull(context, "query context must not be null");

        OrderBy orderBy = context.orderBy();
        if (orderBy == null) {
            return context;
        }

        String ordering = orderingCompiler.compile(orderBy);

        StringBuilder query = context.query();
        if (!query.isEmpty()) {
            query.append(CLAUSE_SEPARATOR);
        }
        query.append(ORDER_BY_KEYWORD).append(ordering);
        return context;
    }

}
