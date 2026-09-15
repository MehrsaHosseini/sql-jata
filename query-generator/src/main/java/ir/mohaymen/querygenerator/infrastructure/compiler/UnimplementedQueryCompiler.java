package ir.mohaymen.querygenerator.infrastructure.compiler;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;

import java.util.Objects;

public abstract class UnimplementedQueryCompiler implements QueryCompiler {

    private final QueryCompilerDialect dialect;

    protected UnimplementedQueryCompiler(QueryCompilerDialect dialect) {
        this.dialect = Objects.requireNonNull(dialect, "compiler dialect must not be null");
    }

    public final QueryCompilerDialect dialect() {
        return dialect;
    }

    private QueryContext unimplemented() {
        throw new UnsupportedOperationException(dialect.name() + " compiler is not implemented yet");
    }

    @Override
    public QueryContext generateSelect(QueryContext context) {
        return unimplemented();
    }

    @Override
    public QueryContext generateFrom(QueryContext context) {
        return unimplemented();
    }

    @Override
    public QueryContext generateJoin(QueryContext context) {
        return unimplemented();
    }

    @Override
    public QueryContext generateWhere(QueryContext context) {
        return unimplemented();
    }

    @Override
    public QueryContext generateGroupBy(QueryContext context) {
        return unimplemented();
    }

    @Override
    public QueryContext generateHaving(QueryContext context) {
        return unimplemented();
    }

    @Override
    public QueryContext generateUnion(QueryContext context) {
        return unimplemented();
    }

    @Override
    public QueryContext generateIntersect(QueryContext context) {
        return unimplemented();
    }

    @Override
    public QueryContext generateMinus(QueryContext context) {
        return unimplemented();
    }

    @Override
    public QueryContext generateOrderBy(QueryContext context) {
        return unimplemented();
    }

    @Override
    public QueryContext generatePagination(QueryContext context) {
        return unimplemented();
    }

    @Override
    public QueryContext generateInsert(QueryContext context) {
        return unimplemented();
    }

    @Override
    public QueryContext generateUpdate(QueryContext context) {
        return unimplemented();
    }

    @Override
    public QueryContext generateDelete(QueryContext context) {
        return unimplemented();
    }
}
