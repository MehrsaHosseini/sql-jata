package ir.mohaymen.querygenerator.api.rest.common.dto;

import ir.mohaymen.querygenerator.domain.schema.enumeration.DisplayFormatting;

public record ColumnRequest(String name, String alias, DisplayFormatting format) {

    public ColumnRequest(String name) {
        this(name, null, null);
    }

    public ColumnRequest(String name, String alias) {
        this(name, alias, null);
    }
}
