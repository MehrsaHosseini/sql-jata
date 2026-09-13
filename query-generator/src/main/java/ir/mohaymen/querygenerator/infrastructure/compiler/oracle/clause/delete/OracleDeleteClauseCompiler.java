package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.delete;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;

public interface OracleDeleteClauseCompiler {
    QueryContext generateDeleteClause(QueryContext context);
}
