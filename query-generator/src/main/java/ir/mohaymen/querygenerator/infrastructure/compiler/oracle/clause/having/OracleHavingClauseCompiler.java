package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;

public interface OracleHavingClauseCompiler {
    QueryContext generateHavingClause(QueryContext context);

}
