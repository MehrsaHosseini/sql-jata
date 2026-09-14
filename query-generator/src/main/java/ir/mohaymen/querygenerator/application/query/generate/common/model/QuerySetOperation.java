package ir.mohaymen.querygenerator.application.query.generate.common.model;

import java.util.Objects;

public record QuerySetOperation(SetOperator operator, QueryContext queryContext) {

    public QuerySetOperation {
        Objects.requireNonNull(operator, "set operator must not be null");
        Objects.requireNonNull(queryContext, "query context must not be null");
    }
}
