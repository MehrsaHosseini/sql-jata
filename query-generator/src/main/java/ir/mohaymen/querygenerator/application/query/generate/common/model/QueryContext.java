package ir.mohaymen.querygenerator.application.query.generate.common.model;

import ir.mohaymen.querygenerator.domain.from.From;
import ir.mohaymen.querygenerator.domain.select.Select;
import ir.mohaymen.querygenerator.domain.where.Where;

public record QueryContext(Select select, From from, Where where, StringBuilder query) {

    public QueryContext(Select select, From from, Where where) {
        this(select, from, where, new StringBuilder());
    }

}
