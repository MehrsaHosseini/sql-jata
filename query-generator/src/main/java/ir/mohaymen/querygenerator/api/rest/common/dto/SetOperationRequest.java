package ir.mohaymen.querygenerator.api.rest.common.dto;

import ir.mohaymen.querygenerator.application.query.generate.common.model.SetOperator;

public record SetOperationRequest(SetOperator operator, SelectQueryRequest query) {
}
