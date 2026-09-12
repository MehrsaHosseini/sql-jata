package ir.mohaymen.querygenerator.domain.schema.column;

import ir.mohaymen.querygenerator.domain.schema.enumeration.DisplayFormatting;

public record Column(String columnName, DisplayFormatting displayFormatting) {
}
