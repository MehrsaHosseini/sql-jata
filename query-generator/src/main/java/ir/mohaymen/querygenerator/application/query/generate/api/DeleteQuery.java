package ir.mohaymen.querygenerator.application.query.generate.api;

import ir.mohaymen.querygenerator.application.query.generate.QueryGeneratorRequest;
import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.domain.delete.DeleteAll;
import ir.mohaymen.querygenerator.domain.schema.table.Table;
import ir.mohaymen.querygenerator.domain.where.Where;

import java.util.Objects;

public final class DeleteQuery extends SqlStatement<DeleteQuery> {

    private final Table table;
    private Where where;

    DeleteQuery(Table table) {
        this.table = Objects.requireNonNull(table, "table must not be null");
    }

    public DeleteQuery where(Where condition) {
        this.where = this.where == null ? condition : Predicates.and(this.where, condition);
        return this;
    }

    @Override
    public QueryGeneratorRequest toRequest() {
        return new QueryGeneratorRequest(applyBindings(new QueryContext(new DeleteAll(table), where)));
    }
}
