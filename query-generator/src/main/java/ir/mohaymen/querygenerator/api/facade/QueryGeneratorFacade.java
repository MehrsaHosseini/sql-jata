package ir.mohaymen.querygenerator.api.facade;

import ir.mohaymen.querygenerator.api.facade.impl.QueryGeneratorFacadeImpl;
import ir.mohaymen.querygenerator.api.rest.common.dto.DeleteQueryRequest;
import ir.mohaymen.querygenerator.api.rest.common.dto.InsertAllQueryRequest;
import ir.mohaymen.querygenerator.api.rest.common.dto.InsertQueryRequest;
import ir.mohaymen.querygenerator.api.rest.common.dto.QueryResponse;
import ir.mohaymen.querygenerator.api.rest.common.dto.SelectQueryRequest;
import ir.mohaymen.querygenerator.api.rest.common.dto.UpdateQueryRequest;
import ir.mohaymen.querygenerator.api.rest.common.mapping.SqlRequestMapper;
import ir.mohaymen.querygenerator.application.query.builder.SqlStatement;
import ir.mohaymen.querygenerator.application.query.generate.QueryGenerator;

public interface QueryGeneratorFacade {

    static QueryGeneratorFacade oracle() {
        return new QueryGeneratorFacadeImpl(QueryGenerator.oracle(), new SqlRequestMapper());
    }

    QueryResponse select(SelectQueryRequest request);

    QueryResponse insert(InsertQueryRequest request);

    QueryResponse insertAll(InsertAllQueryRequest request);

    QueryResponse update(UpdateQueryRequest request);

    QueryResponse delete(DeleteQueryRequest request);

    QueryResponse generate(SqlStatement<?> statement);
}
