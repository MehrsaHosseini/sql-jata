package ir.mohaymen.querygenerator.application.query.generate.common.model;

import ir.mohaymen.querygenerator.domain.from.From;
import ir.mohaymen.querygenerator.domain.group.GroupBy;
import ir.mohaymen.querygenerator.domain.join.Join;
import ir.mohaymen.querygenerator.domain.limit_offset.Pagination;
import ir.mohaymen.querygenerator.domain.order.OrderBy;
import ir.mohaymen.querygenerator.domain.select.Select;
import ir.mohaymen.querygenerator.domain.where.Where;

import java.util.ArrayList;
import java.util.List;

public record QueryContext(Select select, From from, Join join, Where where, GroupBy groupBy, OrderBy orderBy,
                           Pagination pagination, StringBuilder query, List<Object> parameters) {

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
        this(select, from, join, where, groupBy, orderBy, pagination, new StringBuilder(), new ArrayList<>());
    }

}
