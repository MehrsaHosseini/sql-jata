package ir.mohaymen.querygenerator.api.rest.common.dto;

public record TableRequest(String table, String alias) {

    public TableRequest(String table) {
        this(table, null);
    }
}
