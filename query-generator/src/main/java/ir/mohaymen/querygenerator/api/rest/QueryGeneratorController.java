package ir.mohaymen.querygenerator.api.rest;

import ir.mohaymen.querygenerator.application.query.generate.QueryGenerator;
import ir.mohaymen.querygenerator.api.rest.common.dto.DeleteQueryRequest;
import ir.mohaymen.querygenerator.api.rest.common.dto.InsertAllQueryRequest;
import ir.mohaymen.querygenerator.api.rest.common.dto.InsertQueryRequest;
import ir.mohaymen.querygenerator.api.rest.common.dto.QueryResponse;
import ir.mohaymen.querygenerator.api.rest.common.dto.SelectQueryRequest;
import ir.mohaymen.querygenerator.api.rest.common.dto.UpdateQueryRequest;
import ir.mohaymen.querygenerator.api.rest.common.mapping.SqlRequestMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/queries")
public class QueryGeneratorController {

    private final QueryGenerator queryGenerator;
    private final SqlRequestMapper mapper;

    public QueryGeneratorController(QueryGenerator queryGenerator, SqlRequestMapper mapper) {
        this.queryGenerator = Objects.requireNonNull(queryGenerator, "query generator must not be null");
        this.mapper = Objects.requireNonNull(mapper, "sql request mapper must not be null");
    }

    @PostMapping("/select")
    public QueryResponse select(@RequestBody SelectQueryRequest request) {
        return QueryResponse.from(mapper.toSelect(request).generate(queryGenerator));
    }

    @PostMapping("/insert")
    public QueryResponse insert(@RequestBody InsertQueryRequest request) {
        return QueryResponse.from(mapper.toInsert(request).generate(queryGenerator));
    }

    @PostMapping("/insert-all")
    public QueryResponse insertAll(@RequestBody InsertAllQueryRequest request) {
        return QueryResponse.from(mapper.toInsertAll(request).generate(queryGenerator));
    }

    @PostMapping("/update")
    public QueryResponse update(@RequestBody UpdateQueryRequest request) {
        return QueryResponse.from(mapper.toUpdate(request).generate(queryGenerator));
    }

    @PostMapping("/delete")
    public QueryResponse delete(@RequestBody DeleteQueryRequest request) {
        return QueryResponse.from(mapper.toDelete(request).generate(queryGenerator));
    }
}
