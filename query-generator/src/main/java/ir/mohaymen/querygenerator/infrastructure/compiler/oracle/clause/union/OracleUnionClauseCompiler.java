package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.union;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;

public interface OracleUnionClauseCompiler {
    QueryContext generateUnionClause(QueryContext context);

}
