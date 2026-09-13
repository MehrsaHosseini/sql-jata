package ir.mohaymen.querygenerator.infrastructure.compiler;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;

public interface QueryCompiler {
    QueryContext generateSelect(QueryContext context);
    QueryContext generateFrom(QueryContext context);
    QueryContext generateJoin(QueryContext context);
    QueryContext generateWhere(QueryContext context);
    QueryContext generateGroupBy(QueryContext context);
    QueryContext generateOrderBy(QueryContext context);
    QueryContext generatePagination(QueryContext context);
}
