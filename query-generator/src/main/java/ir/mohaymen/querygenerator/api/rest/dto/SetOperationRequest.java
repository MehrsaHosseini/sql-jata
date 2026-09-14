package ir.mohaymen.querygenerator.api.rest.dto;

import ir.mohaymen.querygenerator.application.query.generate.common.model.SetOperator;

public record SetOperationRequest(SetOperator operator, SelectQueryRequest query) {
}
