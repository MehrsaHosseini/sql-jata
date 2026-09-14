package ir.mohaymen.querygenerator.application.query.builder;

import ir.mohaymen.querygenerator.application.query.generate.QueryGeneratorRequest;
import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.domain.insert.InsertSingleRow;
import ir.mohaymen.querygenerator.domain.schema.column.Column;
import ir.mohaymen.querygenerator.domain.schema.table.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class InsertQuery extends SqlStatement<InsertQuery> {

    private final Table table;
    private final List<Column> columns = new ArrayList<>();
    private final List<Object> values = new ArrayList<>();

    InsertQuery(Table table) {
        this.table = Objects.requireNonNull(table, "table must not be null");
    }

    public InsertQuery columns(String... names) {
        this.columns.clear();
        this.columns.addAll(Expressions.columns(names));
        return this;
    }

    public InsertQuery columns(Column... columns) {
        this.columns.clear();
        this.columns.addAll(List.of(columns));
        return this;
    }

    public InsertQuery values(Object... values) {
        this.values.clear();
        if (values != null) {
            Collections.addAll(this.values, values);
        }
        return this;
    }

    InsertSingleRow toRow() {
        if (values.isEmpty()) {
            throw new IllegalStateException("insert values must not be empty");
        }
        if (!columns.isEmpty() && columns.size() != values.size()) {
            throw new IllegalStateException("insert columns and values must have the same size");
        }
        return new InsertSingleRow(table, columns.isEmpty() ? null : List.copyOf(columns), new ArrayList<>(values));
    }

    @Override
    public QueryGeneratorRequest toRequest() {
        return new QueryGeneratorRequest(applyBindings(new QueryContext(toRow())));
    }
}
