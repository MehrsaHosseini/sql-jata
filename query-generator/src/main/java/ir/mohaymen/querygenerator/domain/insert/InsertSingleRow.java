package ir.mohaymen.querygenerator.domain.insert;

import ir.mohaymen.querygenerator.domain.schema.column.Column;
import ir.mohaymen.querygenerator.domain.schema.table.Table;

import java.util.List;

public record InsertSingleRow(Table table, List<Column> columnList, List<Object> values) implements Insert {

    public InsertSingleRow(Table table, List<Object> values) {
        this(table, null, values);
    }

}
