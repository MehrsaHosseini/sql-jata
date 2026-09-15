package ir.mohaymen.querygenerator.api.common.dto;

import ir.mohaymen.querygenerator.domain.schema.enumeration.SortDirection;

public record OrderRequest(String column, SortDirection direction) {

    public OrderRequest(String column) {
        this(column, null);
    }
}
