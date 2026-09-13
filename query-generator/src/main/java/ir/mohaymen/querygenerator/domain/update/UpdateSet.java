package ir.mohaymen.querygenerator.domain.update;

import ir.mohaymen.querygenerator.domain.schema.column.Column;

public record UpdateSet(Column column, Object value) {
}
