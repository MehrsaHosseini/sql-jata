package ir.mohaymen.querygenerator.api.public_interface;

import ir.mohaymen.querygenerator.api.public_interface.impl.QueryGeneratorFacadeImpl;
import ir.mohaymen.querygenerator.api.common.dto.DeleteQueryRequest;
import ir.mohaymen.querygenerator.api.common.dto.InsertAllQueryRequest;
import ir.mohaymen.querygenerator.api.common.dto.InsertQueryRequest;
import ir.mohaymen.querygenerator.api.common.dto.QueryResponse;
import ir.mohaymen.querygenerator.api.common.dto.SelectQueryRequest;
import ir.mohaymen.querygenerator.api.common.dto.UpdateQueryRequest;
import ir.mohaymen.querygenerator.api.common.mapping.SqlRequestMapper;
import ir.mohaymen.querygenerator.application.query.builder.SqlStatement;
import ir.mohaymen.querygenerator.application.query.generate.QueryGenerator;
import ir.mohaymen.querygenerator.infrastructure.compiler.QueryCompilerDialect;

public interface QueryGeneratorFacade {

    static QueryGeneratorFacade oracle() {
        return of(QueryCompilerDialect.ORACLE);
    }

    static QueryGeneratorFacade of(QueryCompilerDialect dialect) {
        return new QueryGeneratorFacadeImpl(QueryGenerator.of(dialect), new SqlRequestMapper());
    }

    QueryResponse select(SelectQueryRequest request);

    QueryResponse insert(InsertQueryRequest request);

    QueryResponse insertAll(InsertAllQueryRequest request);

    QueryResponse update(UpdateQueryRequest request);

    QueryResponse delete(DeleteQueryRequest request);

    QueryResponse generate(SqlStatement<?> statement);
}
