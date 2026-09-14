package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.order_by;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;

public interface OracleOrderByClauseCompiler {
    QueryContext generateOrderByClause(QueryContext context);
}
