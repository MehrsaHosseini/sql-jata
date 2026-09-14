package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.group_by;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;

public interface OracleGroupByClauseCompiler {
    QueryContext generateGroupByClause(QueryContext context);
}
