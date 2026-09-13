package ir.mohaymen.querygenerator.domain.update;

import ir.mohaymen.querygenerator.domain.schema.table.Table;

import java.util.List;

public record UpdateAll(Table table, List<UpdateSet> setList) implements Update {
}
