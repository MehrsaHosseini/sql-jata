package ir.mohaymen.querygenerator.domain.where;

import ir.mohaymen.querygenerator.domain.schema.column.Column;

public final class WhereNull implements Where {
    private Column column;
    private Boolean negative;
}
