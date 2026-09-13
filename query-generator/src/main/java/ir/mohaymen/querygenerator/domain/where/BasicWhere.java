package ir.mohaymen.querygenerator.domain.where;

import ir.mohaymen.querygenerator.domain.schema.column.Column;

public record BasicWhere(Column column, String operation, Object value) implements Where {
}
