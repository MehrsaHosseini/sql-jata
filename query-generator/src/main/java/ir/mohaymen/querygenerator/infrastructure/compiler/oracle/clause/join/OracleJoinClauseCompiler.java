package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;

public interface OracleJoinClauseCompiler {
    QueryContext generateJoinClause(QueryContext context);

}
