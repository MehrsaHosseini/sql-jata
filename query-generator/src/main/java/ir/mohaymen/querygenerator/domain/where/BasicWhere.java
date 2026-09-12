package ir.mohaymen.querygenerator.domain.where;

import ir.mohaymen.querygenerator.domain.schema.column.Column;

public final class BasicWhere implements Where{
    private Column column;
    private String operation;
    private Object value;
}
