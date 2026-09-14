package ir.mohaymen.querygenerator.infrastructure.compiler.oracle;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.infrastructure.compiler.QueryCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.delete.OracleDeleteClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.delete.impl.OracleDeleteClauseCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.from.OracleFromClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.from.impl.OracleFromClauseCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.group_by.OracleGroupByClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.group_by.impl.OracleGroupByClauseCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.OracleHavingClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.impl.OracleHavingClauseCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.OracleInsertClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.impl.OracleInsertClauseCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.intersect.OracleIntersectClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.intersect.impl.OracleIntersectClauseCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.OracleJoinClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.impl.OracleJoinClauseCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.minus.OracleMinusClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.minus.impl.OracleMinusClauseCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.order_by.OracleOrderByClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.order_by.impl.OracleOrderByClauseCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.OraclePaginationClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.impl.OraclePaginationClauseCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.OracleSelectClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.impl.OracleSelectClauseCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.union.OracleUnionClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.union.impl.OracleUnionClauseCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.update.OracleUpdateClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.update.impl.OracleUpdateClauseCompilerImpl;
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
    private final OracleUnionClauseCompiler unionClauseCompiler;
    private final OracleIntersectClauseCompiler intersectClauseCompiler;
    private final OracleMinusClauseCompiler minusClauseCompiler;
    private final OracleOrderByClauseCompiler orderByClauseCompiler;
    private final OraclePaginationClauseCompiler paginationClauseCompiler;
    private final OracleInsertClauseCompiler insertClauseCompiler;
    private final OracleUpdateClauseCompiler updateClauseCompiler;
    private final OracleDeleteClauseCompiler deleteClauseCompiler;

    public OracleQueryCompiler() {
        this(new OracleSelectClauseCompilerImpl(),
                new OracleFromClauseCompilerImpl(),
                new OracleJoinClauseCompilerImpl(),
                new OracleWhereClauseCompilerImpl(),
                new OracleGroupByClauseCompilerImpl(),
                new OracleHavingClauseCompilerImpl(),
                new OracleUnionClauseCompilerImpl(),
                new OracleIntersectClauseCompilerImpl(),
                new OracleMinusClauseCompilerImpl(),
                new OracleOrderByClauseCompilerImpl(),
                new OraclePaginationClauseCompilerImpl(),
                new OracleInsertClauseCompilerImpl(),
                new OracleUpdateClauseCompilerImpl(),
                new OracleDeleteClauseCompilerImpl());
    }

    public OracleQueryCompiler(OracleSelectClauseCompiler selectClauseCompiler,
                               OracleFromClauseCompiler fromClauseCompiler,
                               OracleJoinClauseCompiler joinClauseCompiler,
                               OracleWhereClauseCompiler whereClauseCompiler,
                               OracleGroupByClauseCompiler groupByClauseCompiler,
                               OracleHavingClauseCompiler havingClauseCompiler,
                               OracleUnionClauseCompiler unionClauseCompiler,
                               OracleIntersectClauseCompiler intersectClauseCompiler,
                               OracleMinusClauseCompiler minusClauseCompiler,
                               OracleOrderByClauseCompiler orderByClauseCompiler,
                               OraclePaginationClauseCompiler paginationClauseCompiler,
                               OracleInsertClauseCompiler insertClauseCompiler,
                               OracleUpdateClauseCompiler updateClauseCompiler,
                               OracleDeleteClauseCompiler deleteClauseCompiler) {
        this.selectClauseCompiler = Objects.requireNonNull(selectClauseCompiler, "select clause compiler must not be null");
        this.fromClauseCompiler = Objects.requireNonNull(fromClauseCompiler, "from clause compiler must not be null");
        this.joinClauseCompiler = Objects.requireNonNull(joinClauseCompiler, "join clause compiler must not be null");
        this.whereClauseCompiler = Objects.requireNonNull(whereClauseCompiler, "where clause compiler must not be null");
        this.groupByClauseCompiler = Objects.requireNonNull(groupByClauseCompiler, "group by clause compiler must not be null");
        this.havingClauseCompiler = Objects.requireNonNull(havingClauseCompiler, "having clause compiler must not be null");
        this.unionClauseCompiler = Objects.requireNonNull(unionClauseCompiler, "union clause compiler must not be null");
        this.intersectClauseCompiler = Objects.requireNonNull(intersectClauseCompiler, "intersect clause compiler must not be null");
        this.minusClauseCompiler = Objects.requireNonNull(minusClauseCompiler, "minus clause compiler must not be null");
        this.orderByClauseCompiler = Objects.requireNonNull(orderByClauseCompiler, "order by clause compiler must not be null");
        this.paginationClauseCompiler = Objects.requireNonNull(paginationClauseCompiler, "pagination clause compiler must not be null");
        this.insertClauseCompiler = Objects.requireNonNull(insertClauseCompiler, "insert clause compiler must not be null");
        this.updateClauseCompiler = Objects.requireNonNull(updateClauseCompiler, "update clause compiler must not be null");
        this.deleteClauseCompiler = Objects.requireNonNull(deleteClauseCompiler, "delete clause compiler must not be null");
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
    public QueryContext generateUnion(QueryContext context) {
        return unionClauseCompiler.generateUnionClause(context);
    }

    @Override
    public QueryContext generateIntersect(QueryContext context) {
        return intersectClauseCompiler.generateIntersectClause(context);
    }

    @Override
    public QueryContext generateMinus(QueryContext context) {
        return minusClauseCompiler.generateMinusClause(context);
    }

    @Override
    public QueryContext generateOrderBy(QueryContext context) {
        return orderByClauseCompiler.generateOrderByClause(context);
    }

    @Override
    public QueryContext generatePagination(QueryContext context) {
        return paginationClauseCompiler.generatePaginationClause(context);
    }

    @Override
    public QueryContext generateInsert(QueryContext context) {
        return insertClauseCompiler.generateInsertClause(context);
    }

    @Override
    public QueryContext generateUpdate(QueryContext context) {
        return updateClauseCompiler.generateUpdateClause(context);
    }

    @Override
    public QueryContext generateDelete(QueryContext context) {
        return deleteClauseCompiler.generateDeleteClause(context);
    }
}
