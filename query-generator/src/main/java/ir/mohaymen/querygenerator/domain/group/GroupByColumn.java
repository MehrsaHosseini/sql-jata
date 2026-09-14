package ir.mohaymen.querygenerator.domain.group;

import ir.mohaymen.querygenerator.domain.schema.column.Column;

import java.util.List;

public record GroupByColumn(List<Column> columnList) implements GroupBy {
}
