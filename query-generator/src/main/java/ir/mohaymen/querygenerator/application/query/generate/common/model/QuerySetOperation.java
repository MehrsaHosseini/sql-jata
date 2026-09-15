package ir.mohaymen.querygenerator.application.query.generate.common.model;

import java.util.List;
import java.util.Objects;

public record QuerySetOperation(SetOperator operator, QueryContext queryContext, List<QuerySetOperation> setOperations) {

    public QuerySetOperation {
        Objects.requireNonNull(operator, "set operator must not be null");
        Objects.requireNonNull(queryContext, "query context must not be null");
        setOperations = setOperations == null || setOperations.isEmpty()
                ? List.of()
                : List.copyOf(setOperations);
    }

    public QuerySetOperation(SetOperator operator, QueryContext queryContext) {
        this(operator, queryContext, List.of());
    }
}
