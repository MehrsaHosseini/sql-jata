package ir.mohaymen.querygenerator.domain.select;

import ir.mohaymen.querygenerator.domain.schema.column.Column;

import java.util.List;

public record SelectColumn(List<Column> columnList) implements Select {
}
