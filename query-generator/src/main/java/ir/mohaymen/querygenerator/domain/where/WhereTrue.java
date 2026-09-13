package ir.mohaymen.querygenerator.domain.where;

import ir.mohaymen.querygenerator.domain.schema.column.Column;

public record WhereTrue(Column column, Boolean negative) implements Where {
}
