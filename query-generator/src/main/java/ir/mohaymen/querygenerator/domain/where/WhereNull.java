package ir.mohaymen.querygenerator.domain.where;

import ir.mohaymen.querygenerator.domain.schema.column.Column;

public record WhereNull(Column column, Boolean isNull) implements Where {
}
