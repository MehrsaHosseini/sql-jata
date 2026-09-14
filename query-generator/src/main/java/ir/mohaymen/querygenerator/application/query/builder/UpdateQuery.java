package ir.mohaymen.querygenerator.application.query.builder;

import ir.mohaymen.querygenerator.application.query.generate.QueryGeneratorRequest;
import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.domain.schema.table.Table;
import ir.mohaymen.querygenerator.domain.update.UpdateAll;
import ir.mohaymen.querygenerator.domain.update.UpdateSet;
import ir.mohaymen.querygenerator.domain.where.Where;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class UpdateQuery extends SqlStatement<UpdateQuery> {

    private final Table table;
    private final List<UpdateSet> assignments = new ArrayList<>();
    private Where where;

    UpdateQuery(Table table) {
        this.table = Objects.requireNonNull(table, "table must not be null");
    }

    public UpdateQuery set(String column, Object value) {
        assignments.add(new UpdateSet(Expressions.col(column), value));
        return this;
    }

    public UpdateQuery setNull(String column) {
        return set(column, null);
    }

    public UpdateQuery where(Where condition) {
        this.where = this.where == null ? condition : Predicates.and(this.where, condition);
        return this;
    }

    @Override
    public QueryGeneratorRequest toRequest() {
        if (assignments.isEmpty()) {
            throw new IllegalStateException("update needs at least one assignment");
        }
        return new QueryGeneratorRequest(applyBindings(new QueryContext(new UpdateAll(table, List.copyOf(assignments)), where)));
    }
}
