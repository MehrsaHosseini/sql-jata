package ir.mohaymen.querygenerator.application.query.generate;

import ir.mohaymen.querygenerator.application.query.generate.common.model.Query;
import ir.mohaymen.querygenerator.application.query.generate.impl.QueryGeneratorRequestHandlerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.QueryCompilerDialect;
import ir.mohaymen.querygenerator.infrastructure.compiler.QueryCompilerFactory;

import java.util.Objects;

public class QueryGenerator {

    private final QueryGeneratorRequestHandler handler;

    public QueryGenerator(QueryGeneratorRequestHandler handler) {
        this.handler = Objects.requireNonNull(handler, "query generator request handler must not be null");
    }

    public static QueryGenerator oracle() {
        return of(QueryCompilerDialect.ORACLE);
    }

    public static QueryGenerator of(QueryCompilerDialect dialect) {
        return new QueryGenerator(new QueryGeneratorRequestHandlerImpl(QueryCompilerFactory.create(dialect)));
    }

    public Query generate(QueryGeneratorRequest request) {
        return handler.generateQuery(request);
    }
}
