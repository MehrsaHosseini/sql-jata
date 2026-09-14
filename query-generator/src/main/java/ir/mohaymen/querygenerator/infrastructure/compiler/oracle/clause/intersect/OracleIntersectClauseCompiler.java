package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.intersect;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;

public interface OracleIntersectClauseCompiler {
    QueryContext generateIntersectClause(QueryContext context);

}
