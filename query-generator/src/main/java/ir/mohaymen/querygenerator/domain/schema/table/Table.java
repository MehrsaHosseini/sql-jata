package ir.mohaymen.querygenerator.domain.schema.table;

public record Table(String tableName, String tableAlias) {

    public Table(String tableName) {
        this(tableName, null);
    }

}
