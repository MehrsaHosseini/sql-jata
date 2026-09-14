package ir.mohaymen.querygenerator.domain.where;

import ir.mohaymen.querygenerator.domain.schema.column.Column;
import ir.mohaymen.querygenerator.domain.schema.enumeration.DatePart;

public record WhereDatePart(Column column, DatePart datePart, String operation, Object value) implements Where {
}
