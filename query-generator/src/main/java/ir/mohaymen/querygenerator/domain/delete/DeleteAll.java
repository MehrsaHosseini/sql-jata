package ir.mohaymen.querygenerator.domain.delete;

import ir.mohaymen.querygenerator.domain.schema.table.Table;

public record DeleteAll(Table table) implements Delete {
}
