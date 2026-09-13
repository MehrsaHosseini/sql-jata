package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.update;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;

public interface OracleUpdateClauseCompiler {
    QueryContext generateUpdateClause(QueryContext context);
}
