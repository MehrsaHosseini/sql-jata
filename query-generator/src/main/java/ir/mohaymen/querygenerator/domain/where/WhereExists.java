package ir.mohaymen.querygenerator.domain.where;

import ir.mohaymen.querygenerator.domain.schema.table.Table;

public record WhereExists(Table table, Where where) implements Where {
}
