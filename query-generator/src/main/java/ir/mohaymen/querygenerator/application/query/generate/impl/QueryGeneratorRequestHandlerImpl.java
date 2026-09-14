package ir.mohaymen.querygenerator.application.query.generate.impl;

import ir.mohaymen.querygenerator.application.query.generate.QueryGeneratorRequest;
import ir.mohaymen.querygenerator.application.query.generate.QueryGeneratorRequestHandler;
import ir.mohaymen.querygenerator.application.query.generate.common.model.Query;
import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.application.query.generate.common.model.QuerySetOperation;
import ir.mohaymen.querygenerator.application.query.generate.common.model.SetOperator;
import ir.mohaymen.querygenerator.domain.intersect.Intersect;
import ir.mohaymen.querygenerator.domain.intersect.IntersectDistinct;
import ir.mohaymen.querygenerator.domain.minus.Minus;
import ir.mohaymen.querygenerator.domain.minus.MinusDistinct;
import ir.mohaymen.querygenerator.domain.union.Union;
import ir.mohaymen.querygenerator.domain.union.UnionAll;
import ir.mohaymen.querygenerator.domain.union.UnionDistinct;
import ir.mohaymen.querygenerator.infrastructure.compiler.QueryCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.OracleQueryCompiler;

import java.util.Objects;

public class QueryGeneratorRequestHandlerImpl implements QueryGeneratorRequestHandler {

    private final QueryCompiler compiler;

    public QueryGeneratorRequestHandlerImpl() {
        this(new OracleQueryCompiler());
    }

    public QueryGeneratorRequestHandlerImpl(QueryCompiler compiler) {
        this.compiler = Objects.requireNonNull(compiler, "query compiler must not be null");
    }

    @Override
    public Query generateQuery(QueryGeneratorRequest request) {
        Objects.requireNonNull(request, "request must not be null");

        QueryContext context = request.queryContext();
        StatementType statementType = statementType(context);
        if (statementType != StatementType.SELECT && !request.setOperations().isEmpty()) {
            throw new IllegalArgumentException("set operations can only be applied to a select");
        }

        return switch (statementType) {
            case INSERT -> compileInsert(context);
            case UPDATE -> compileUpdate(context);
            case DELETE -> compileDelete(context);
            case SELECT -> compileSelect(context, request);
        };
    }

    private Query compileSelect(QueryContext context, QueryGeneratorRequest request) {
        compileSelectBody(context);
        for (QuerySetOperation setOperation : request.setOperations()) {
            applySetOperator(context, setOperation.operator());
            compileSelectBody(setOperation.queryContext()
                    .asSelectBody(context.query(), context.parameters(), context.parameterMode()));
        }
        compiler.generateOrderBy(context);
        compiler.generatePagination(context);
        return toQuery(context);
    }

    private void compileSelectBody(QueryContext context) {
        compiler.generateSelect(context);
        compiler.generateFrom(context);
        compiler.generateJoin(context);
        compiler.generateWhere(context);
        compiler.generateGroupBy(context);
        compiler.generateHaving(context);
    }

    private void applySetOperator(QueryContext shared, SetOperator operator) {
        QueryContext marker = switch (operator) {
            case UNION -> operatorContext(shared, new UnionDistinct(), null, null);
            case UNION_ALL -> operatorContext(shared, new UnionAll(), null, null);
            case INTERSECT -> operatorContext(shared, null, new IntersectDistinct(), null);
            case MINUS -> operatorContext(shared, null, null, new MinusDistinct());
        };
        switch (operator) {
            case UNION, UNION_ALL -> compiler.generateUnion(marker);
            case INTERSECT -> compiler.generateIntersect(marker);
            case MINUS -> compiler.generateMinus(marker);
        }
    }

    private QueryContext operatorContext(QueryContext shared, Union union, Intersect intersect, Minus minus) {
        return new QueryContext(null, null, null, null, null, null, null, null, null, null, null,
                union, intersect, minus, shared.query(), shared.parameters(), shared.parameterMode());
    }

    private Query compileInsert(QueryContext context) {
        compiler.generateInsert(context);
        return toQuery(context);
    }

    private Query compileUpdate(QueryContext context) {
        compiler.generateUpdate(context);
        compiler.generateWhere(context);
        return toQuery(context);
    }

    private Query compileDelete(QueryContext context) {
        compiler.generateDelete(context);
        compiler.generateWhere(context);
        return toQuery(context);
    }

    private static Query toQuery(QueryContext context) {
        return new Query(context.query().toString(), context.parameters());
    }

    private static StatementType statementType(QueryContext context) {
        boolean insert = context.insert() != null;
        boolean update = context.update() != null;
        boolean delete = context.delete() != null;
        int dmlCount = (insert ? 1 : 0) + (update ? 1 : 0) + (delete ? 1 : 0);
        if (dmlCount > 1) {
            throw new IllegalArgumentException("a request can contain only one of insert, update, or delete");
        }
        if (dmlCount == 1 && context.select() != null) {
            throw new IllegalArgumentException("a request cannot mix a select with insert, update, or delete");
        }
        if (insert) {
            return StatementType.INSERT;
        }
        if (update) {
            return StatementType.UPDATE;
        }
        if (delete) {
            return StatementType.DELETE;
        }
        return StatementType.SELECT;
    }

    private enum StatementType {
        SELECT,
        INSERT,
        UPDATE,
        DELETE
    }
}
