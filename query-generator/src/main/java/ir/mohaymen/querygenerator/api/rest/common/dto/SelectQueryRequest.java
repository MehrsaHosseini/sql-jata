package ir.mohaymen.querygenerator.api.rest.common.dto;

import ir.mohaymen.querygenerator.domain.parameter.ParameterMode;

import java.util.List;
import java.util.Map;

public record SelectQueryRequest(
        List<ColumnRequest> columns,
        RawSqlRequest selectRaw,
        TableRequest from,
        RawSqlRequest fromRaw,
        List<JoinRequest> joins,
        PredicateRequest where,
        List<String> groupBy,
        RawSqlRequest groupByRaw,
        HavingRequest having,
        List<OrderRequest> orderBy,
        RawSqlRequest orderByRaw,
        Integer limit,
        Integer offset,
        Integer page,
        Integer pageSize,
        List<SetOperationRequest> setOperations,
        ParameterMode parameterMode,
        Map<String, Object> bind
) {
}
