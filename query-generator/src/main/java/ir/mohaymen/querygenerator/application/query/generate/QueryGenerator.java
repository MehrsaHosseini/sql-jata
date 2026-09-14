package ir.mohaymen.querygenerator.application.query.generate;

import ir.mohaymen.querygenerator.application.query.generate.api.SqlStatement;
import ir.mohaymen.querygenerator.application.query.generate.common.model.Query;
import ir.mohaymen.querygenerator.application.query.generate.impl.QueryGeneratorRequestHandlerImpl;

import java.util.Objects;

public class QueryGenerator {

    private final QueryGeneratorRequestHandler handler;

    public QueryGenerator(QueryGeneratorRequestHandler handler) {
        this.handler = Objects.requireNonNull(handler, "query generator request handler must not be null");
    }

    public static QueryGenerator oracle() {
        return new QueryGenerator(new QueryGeneratorRequestHandlerImpl());
    }

    public Query generate(QueryGeneratorRequest request) {
        return handler.generateQuery(request);
    }

    public Query generate(SqlStatement<?> statement) {
        Objects.requireNonNull(statement, "statement must not be null");
        return generate(statement.toRequest());
    }
}
