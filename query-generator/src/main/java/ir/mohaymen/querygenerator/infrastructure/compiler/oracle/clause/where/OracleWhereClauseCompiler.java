package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;

public interface OracleWhereClauseCompiler {
    QueryContext generateWhereClause(QueryContext context);
}
