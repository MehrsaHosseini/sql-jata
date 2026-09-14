package ir.mohaymen.querygenerator.domain.order;

import java.util.List;

public record OrderByColumn(List<OrderByItem> columnList) implements OrderBy {
}
