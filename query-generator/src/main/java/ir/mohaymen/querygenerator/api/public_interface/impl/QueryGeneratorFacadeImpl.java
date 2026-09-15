package ir.mohaymen.querygenerator.api.public_interface.impl;

import ir.mohaymen.querygenerator.api.public_interface.QueryGeneratorFacade;
import ir.mohaymen.querygenerator.api.common.dto.DeleteQueryRequest;
import ir.mohaymen.querygenerator.api.common.dto.InsertAllQueryRequest;
import ir.mohaymen.querygenerator.api.common.dto.InsertQueryRequest;
import ir.mohaymen.querygenerator.api.common.dto.QueryResponse;
import ir.mohaymen.querygenerator.api.common.dto.SelectQueryRequest;
import ir.mohaymen.querygenerator.api.common.dto.UpdateQueryRequest;
import ir.mohaymen.querygenerator.api.common.mapping.SqlRequestMapper;
import ir.mohaymen.querygenerator.application.query.builder.SqlStatement;
import ir.mohaymen.querygenerator.application.query.generate.QueryGenerator;

import java.util.Objects;

public class QueryGeneratorFacadeImpl implements QueryGeneratorFacade {

    private final QueryGenerator queryGenerator;
    private final SqlRequestMapper mapper;

    public QueryGeneratorFacadeImpl(QueryGenerator queryGenerator, SqlRequestMapper mapper) {
        this.queryGenerator = Objects.requireNonNull(queryGenerator, "query generator must not be null");
        this.mapper = Objects.requireNonNull(mapper, "sql request mapper must not be null");
    }

    @Override
    public QueryResponse select(SelectQueryRequest request) {
        return generate(mapper.toSelect(request));
    }

    @Override
    public QueryResponse insert(InsertQueryRequest request) {
        return generate(mapper.toInsert(request));
    }

    @Override
    public QueryResponse insertAll(InsertAllQueryRequest request) {
        return generate(mapper.toInsertAll(request));
    }

    @Override
    public QueryResponse update(UpdateQueryRequest request) {
        return generate(mapper.toUpdate(request));
    }

    @Override
    public QueryResponse delete(DeleteQueryRequest request) {
        return generate(mapper.toDelete(request));
    }

    @Override
    public QueryResponse generate(SqlStatement<?> statement) {
        return QueryResponse.from(Objects.requireNonNull(statement, "statement must not be null")
                .generate(queryGenerator));
    }
}
