package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.from;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;

public interface OracleFromClauseCompiler {
    QueryContext generateFromClause(QueryContext context);

}
