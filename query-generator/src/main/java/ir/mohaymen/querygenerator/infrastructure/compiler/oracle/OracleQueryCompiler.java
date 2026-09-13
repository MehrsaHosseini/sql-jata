package ir.mohaymen.querygenerator.infrastructure.compiler.oracle;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.infrastructure.compiler.QueryCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.from.OracleFromClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.from.impl.OracleFromClauseCompilerImpl;
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
    private final OracleWhereClauseCompiler whereClauseCompiler;
    private final OraclePaginationClauseCompiler paginationClauseCompiler;

    public OracleQueryCompiler() {
        this(new OracleSelectClauseCompilerImpl(),
                new OracleFromClauseCompilerImpl(),
                new OracleWhereClauseCompilerImpl(),
                new OraclePaginationClauseCompilerImpl());
    }

    public OracleQueryCompiler(OracleSelectClauseCompiler selectClauseCompiler,
                               OracleFromClauseCompiler fromClauseCompiler,
                               OracleWhereClauseCompiler whereClauseCompiler,
                               OraclePaginationClauseCompiler paginationClauseCompiler) {
        this.selectClauseCompiler = Objects.requireNonNull(selectClauseCompiler, "select clause compiler must not be null");
        this.fromClauseCompiler = Objects.requireNonNull(fromClauseCompiler, "from clause compiler must not be null");
        this.whereClauseCompiler = Objects.requireNonNull(whereClauseCompiler, "where clause compiler must not be null");
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
    public QueryContext generateWhere(QueryContext context) {
        return whereClauseCompiler.generateWhereClause(context);
    }

    @Override
    public QueryContext generatePagination(QueryContext context) {
        return paginationClauseCompiler.generatePaginationClause(context);
    }
}
