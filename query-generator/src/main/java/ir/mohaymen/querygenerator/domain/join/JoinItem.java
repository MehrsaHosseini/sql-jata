package ir.mohaymen.querygenerator.domain.join;

import ir.mohaymen.querygenerator.domain.schema.enumeration.JoinType;
import ir.mohaymen.querygenerator.domain.schema.table.Table;

import java.util.List;

public record JoinItem(JoinType joinType, Table table, List<JoinOn> onList) implements Join {

    public JoinItem(JoinType joinType, Table table) {
        this(joinType, table, null);
    }

}
