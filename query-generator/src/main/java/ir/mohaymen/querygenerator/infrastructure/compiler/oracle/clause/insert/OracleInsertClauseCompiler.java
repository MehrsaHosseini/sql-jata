package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;

public interface OracleInsertClauseCompiler {
    QueryContext generateInsertClause(QueryContext context);
}
