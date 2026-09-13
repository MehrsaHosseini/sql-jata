package ir.mohaymen.querygenerator.domain.join;

import ir.mohaymen.querygenerator.domain.schema.column.Column;


public record JoinOn(Column leftColumn, String operation, Column rightColumn) {

    private static final String EQUAL_OPERATION = "=";

    public JoinOn(Column leftColumn, Column rightColumn) {
        this(leftColumn, EQUAL_OPERATION, rightColumn);
    }

}
