package ir.mohaymen.querygenerator.application.query.generate.common.model;

import ir.mohaymen.querygenerator.domain.common.NamedParameters;
import ir.mohaymen.querygenerator.domain.delete.Delete;
import ir.mohaymen.querygenerator.domain.from.From;
import ir.mohaymen.querygenerator.domain.group.GroupBy;
import ir.mohaymen.querygenerator.domain.having.Having;
import ir.mohaymen.querygenerator.domain.insert.Insert;
import ir.mohaymen.querygenerator.domain.intersect.Intersect;
import ir.mohaymen.querygenerator.domain.join.Join;
import ir.mohaymen.querygenerator.domain.limit_offset.Pagination;
import ir.mohaymen.querygenerator.domain.minus.Minus;
import ir.mohaymen.querygenerator.domain.order.OrderBy;
import ir.mohaymen.querygenerator.domain.parameter.ParameterMode;
import ir.mohaymen.querygenerator.domain.select.Select;
import ir.mohaymen.querygenerator.domain.union.Union;
import ir.mohaymen.querygenerator.domain.update.Update;
import ir.mohaymen.querygenerator.domain.where.Where;

import java.util.LinkedHashMap;
import java.util.Map;

public record QueryContext(Select select, From from, Join join, Where where, GroupBy groupBy, Having having,
                           OrderBy orderBy, Pagination pagination, Insert insert, Update update, Delete delete,
                           Union union, Intersect intersect, Minus minus, StringBuilder query,
                           Map<String, Object> parameters, ParameterMode parameterMode) {

    public QueryContext {
        if (query == null) {
            query = new StringBuilder();
        }
        if (parameters == null) {
            parameters = new LinkedHashMap<>();
        }
        if (parameterMode == null) {
            parameterMode = ParameterMode.NAMED;
        }
    }

    public QueryContext(Select select, From from, Where where) {
        this(select, from, where, null);
    }

    public QueryContext(Select select, From from, Where where, Pagination pagination) {
        this(select, from, null, where, pagination);
    }

    public QueryContext(Select select, From from, Join join, Where where, Pagination pagination) {
        this(select, from, join, where, null, null, pagination);
    }

    public QueryContext(Select select, From from, Join join, Where where, GroupBy groupBy, OrderBy orderBy,
                        Pagination pagination) {
        this(select, from, join, where, groupBy, null, orderBy, pagination);
    }

    public QueryContext(Select select, From from, Join join, Where where, GroupBy groupBy, Having having,
                        OrderBy orderBy, Pagination pagination) {
        this(select, from, join, where, groupBy, having, orderBy, pagination, null, null, null);
    }

    public QueryContext(Insert insert) {
        this(null, null, null, null, null, null, null, null, insert, null, null);
    }

    public QueryContext(Update update) {
        this(update, null);
    }

    public QueryContext(Update update, Where where) {
        this(null, null, null, where, null, null, null, null, null, update, null);
    }

    public QueryContext(Delete delete) {
        this(delete, null);
    }

    public QueryContext(Delete delete, Where where) {
        this(null, null, null, where, null, null, null, null, null, null, delete);
    }

    public QueryContext(Select select, From from, Join join, Where where, GroupBy groupBy, Having having,
                        OrderBy orderBy, Pagination pagination, Insert insert, Update update, Delete delete) {
        this(select, from, join, where, groupBy, having, orderBy, pagination, insert, update, delete,
                null, null, null);
    }

    public QueryContext(Select select, From from, Join join, Where where, GroupBy groupBy, Having having,
                        OrderBy orderBy, Pagination pagination, Insert insert, Update update, Delete delete,
                        Union union, Intersect intersect, Minus minus) {
        this(select, from, join, where, groupBy, having, orderBy, pagination, insert, update, delete,
                union, intersect, minus, new StringBuilder(), new LinkedHashMap<>(), ParameterMode.NAMED);
    }

    public QueryContext(Select select, From from, Join join, Where where, GroupBy groupBy, Having having,
                        OrderBy orderBy, Pagination pagination, Insert insert, Update update, Delete delete,
                        Union union, Intersect intersect, Minus minus, StringBuilder query,
                        Map<String, Object> parameters) {
        this(select, from, join, where, groupBy, having, orderBy, pagination, insert, update, delete,
                union, intersect, minus, query, parameters, ParameterMode.NAMED);
    }

    public QueryContext withParameterMode(ParameterMode parameterMode) {
        return new QueryContext(select, from, join, where, groupBy, having, orderBy, pagination, insert, update, delete,
                union, intersect, minus, query, parameters, parameterMode);
    }

    public QueryContext bind(String name, Object value) {
        NamedParameters.put(parameters, name, value);
        return this;
    }

    public QueryContext bind(Map<String, Object> namedParameters) {
        NamedParameters.putAll(parameters, namedParameters);
        return this;
    }

    public QueryContext asSelectBody(StringBuilder query, Map<String, Object> parameters, ParameterMode parameterMode) {
        return new QueryContext(select, from, join, where, groupBy, having, null, null,
                null, null, null, null, null, null, query, parameters, parameterMode);
    }
}
