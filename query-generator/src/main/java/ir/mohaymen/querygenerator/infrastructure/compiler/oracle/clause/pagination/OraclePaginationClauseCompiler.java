package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;

public interface OraclePaginationClauseCompiler {
    QueryContext generatePaginationClause(QueryContext context);
}
