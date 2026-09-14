package ir.mohaymen.querygenerator.application.query.generate;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.application.query.generate.common.model.QuerySetOperation;

import java.util.List;
import java.util.Objects;

public record QueryGeneratorRequest(QueryContext queryContext, List<QuerySetOperation> setOperations) {

    public QueryGeneratorRequest {
        Objects.requireNonNull(queryContext, "query context must not be null");
        setOperations = setOperations == null || setOperations.isEmpty()
                ? List.of()
                : List.copyOf(setOperations);
    }

    public QueryGeneratorRequest(QueryContext queryContext) {
        this(queryContext, List.of());
    }
}
