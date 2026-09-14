package ir.mohaymen.querygenerator.application.query.generate.api;

import ir.mohaymen.querygenerator.application.query.generate.QueryGeneratorRequest;
import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.domain.insert.InsertAll;
import ir.mohaymen.querygenerator.domain.insert.InsertSingleRow;
import ir.mohaymen.querygenerator.domain.schema.table.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class InsertAllQuery extends SqlStatement<InsertAllQuery> {

    private final List<InsertSingleRow> rows = new ArrayList<>();

    InsertAllQuery() {
    }

    public InsertAllQuery row(InsertQuery row) {
        Objects.requireNonNull(row, "insert row must not be null");
        rows.add(row.toRow());
        return this;
    }

    public InsertAllQuery add(String table, List<String> columns, List<Object> values) {
        return add(Expressions.table(table), columns, values);
    }

    public InsertAllQuery add(Table table, List<String> columns, List<Object> values) {
        Objects.requireNonNull(table, "table must not be null");
        InsertQuery row = new InsertQuery(table).values(values.toArray());
        if (columns != null && !columns.isEmpty()) {
            row.columns(columns.toArray(String[]::new));
        }
        return row(row);
    }

    @Override
    public QueryGeneratorRequest toRequest() {
        if (rows.isEmpty()) {
            throw new IllegalStateException("insert all needs at least one row");
        }
        return new QueryGeneratorRequest(applyBindings(new QueryContext(new InsertAll(List.copyOf(rows)))));
    }
}
