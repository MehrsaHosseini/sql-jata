package ir.mohaymen.querygenerator.domain.schema.column;

import ir.mohaymen.querygenerator.domain.schema.enumeration.DisplayFormatting;

public record Column(String columnName, String columnAlias, DisplayFormatting displayFormatting) {

    public Column(String columnName) {
        this(columnName, null, null);
    }

    public Column(String columnName, String columnAlias) {
        this(columnName, columnAlias, null);
    }

}
