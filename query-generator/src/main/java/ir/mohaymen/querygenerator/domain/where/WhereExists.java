package ir.mohaymen.querygenerator.domain.where;

import ir.mohaymen.querygenerator.domain.schema.table.Table;

public final class WhereExists implements Where {
    private Table table;
    private Where where;
}
