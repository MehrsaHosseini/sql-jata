package ir.mohaymen.querygenerator.infrastructure.compiler.oracle;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.infrastructure.compiler.QueryCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.from.OracleFromClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.from.impl.OracleFromClauseCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.group_by.OracleGroupByClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.group_by.impl.OracleGroupByClauseCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.OracleHavingClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.impl.OracleHavingClauseCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.OracleJoinClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.impl.OracleJoinClauseCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.order_by.OracleOrderByClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.order_by.impl.OracleOrderByClauseCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.OraclePaginationClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.impl.OraclePaginationClauseCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.OracleSelectClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.impl.OracleSelectClauseCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.OracleWhereClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.impl.OracleWhereClauseCompilerImpl;

import java.util.Objects;

public class OracleQueryCompiler implements QueryCompiler {

    private final OracleSelectClauseCompiler selectClauseCompiler;
    private final OracleFromClauseCompiler fromClauseCompiler;
    private final OracleJoinClauseCompiler joinClauseCompiler;
    private final OracleWhereClauseCompiler whereClauseCompiler;
    private final OracleGroupByClauseCompiler groupByClauseCompiler;
    private final OracleHavingClauseCompiler havingClauseCompiler;
    private final OracleOrderByClauseCompiler orderByClauseCompiler;
    private final OraclePaginationClauseCompiler paginationClauseCompiler;

    public OracleQueryCompiler() {
        this(new OracleSelectClauseCompilerImpl(),
                new OracleFromClauseCompilerImpl(),
                new OracleJoinClauseCompilerImpl(),
                new OracleWhereClauseCompilerImpl(),
                new OracleGroupByClauseCompilerImpl(),
                new OracleHavingClauseCompilerImpl(),
                new OracleOrderByClauseCompilerImpl(),
                new OraclePaginationClauseCompilerImpl());
    }

    public OracleQueryCompiler(OracleSelectClauseCompiler selectClauseCompiler,
                               OracleFromClauseCompiler fromClauseCompiler,
                               OracleJoinClauseCompiler joinClauseCompiler,
                               OracleWhereClauseCompiler whereClauseCompiler,
                               OracleGroupByClauseCompiler groupByClauseCompiler,
                               OracleHavingClauseCompiler havingClauseCompiler,
                               OracleOrderByClauseCompiler orderByClauseCompiler,
                               OraclePaginationClauseCompiler paginationClauseCompiler) {
        this.selectClauseCompiler = Objects.requireNonNull(selectClauseCompiler, "select clause compiler must not be null");
        this.fromClauseCompiler = Objects.requireNonNull(fromClauseCompiler, "from clause compiler must not be null");
        this.joinClauseCompiler = Objects.requireNonNull(joinClauseCompiler, "join clause compiler must not be null");
        this.whereClauseCompiler = Objects.requireNonNull(whereClauseCompiler, "where clause compiler must not be null");
        this.groupByClauseCompiler = Objects.requireNonNull(groupByClauseCompiler, "group by clause compiler must not be null");
        this.havingClauseCompiler = Objects.requireNonNull(havingClauseCompiler, "having clause compiler must not be null");
        this.orderByClauseCompiler = Objects.requireNonNull(orderByClauseCompiler, "order by clause compiler must not be null");
        this.paginationClauseCompiler = Objects.requireNonNull(paginationClauseCompiler, "pagination clause compiler must not be null");
    }

    @Override
    public QueryContext generateSelect(QueryContext context) {
        return selectClauseCompiler.generateSelectClause(context);
    }

    @Override
    public QueryContext generateFrom(QueryContext context) {
        return fromClauseCompiler.generateFromClause(context);
    }

    @Override
    public QueryContext generateJoin(QueryContext context) {
        return joinClauseCompiler.generateJoinClause(context);
    }

    @Override
    public QueryContext generateWhere(QueryContext context) {
        return whereClauseCompiler.generateWhereClause(context);
    }

    @Override
    public QueryContext generateGroupBy(QueryContext context) {
        return groupByClauseCompiler.generateGroupByClause(context);
    }

    @Override
    public QueryContext generateHaving(QueryContext context) {
        return havingClauseCompiler.generateHavingClause(context);
    }

    @Override
    public QueryContext generateOrderBy(QueryContext context) {
        return orderByClauseCompiler.generateOrderByClause(context);
    }

    @Override
    public QueryContext generatePagination(QueryContext context) {
        return paginationClauseCompiler.generatePaginationClause(context);
    }
}
