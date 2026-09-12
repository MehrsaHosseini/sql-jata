package ir.mohaymen.querygenerator.application.query.generate;

import ir.mohaymen.querygenerator.application.query.generate.common.model.Query;

public interface QueryGeneratorRequestHandler {
    public Query generateQuery(QueryGeneratorRequest request);
}
