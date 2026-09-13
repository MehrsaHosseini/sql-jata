package ir.mohaymen.querygenerator.domain.join;

import ir.mohaymen.querygenerator.domain.schema.enumeration.JoinType;

public record JoinItem(JoinType joinType) implements Join {
}
