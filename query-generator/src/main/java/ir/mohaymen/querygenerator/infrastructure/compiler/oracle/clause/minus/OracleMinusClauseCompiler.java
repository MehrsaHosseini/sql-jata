package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.minus;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;

public interface OracleMinusClauseCompiler {
    QueryContext generateMinusClause(QueryContext context);

}
