package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;

public interface OracleSelectClauseCompiler {
    QueryContext generateSelectClause(QueryContext context);
}
