package ir.mohaymen.querygenerator.domain.order;

import ir.mohaymen.querygenerator.domain.schema.column.Column;
import ir.mohaymen.querygenerator.domain.schema.enumeration.SortDirection;

public record OrderByItem(Column column, SortDirection direction) {

    public OrderByItem(Column column) {
        this(column, null);
    }

}
