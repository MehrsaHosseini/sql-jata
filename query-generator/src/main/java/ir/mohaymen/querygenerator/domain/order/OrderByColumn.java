package ir.mohaymen.querygenerator.domain.order;

import ir.mohaymen.querygenerator.domain.schema.column.Column;

import java.util.List;

public record OrderByColumn(List<Column> columnList) implements OrderBy {
}
