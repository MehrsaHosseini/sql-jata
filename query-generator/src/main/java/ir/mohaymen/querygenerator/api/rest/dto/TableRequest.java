package ir.mohaymen.querygenerator.api.rest.dto;

public record TableRequest(String table, String alias) {

    public TableRequest(String table) {
        this(table, null);
    }
}
