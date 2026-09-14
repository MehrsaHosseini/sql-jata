package ir.mohaymen.querygenerator.application.query.builder;

import ir.mohaymen.querygenerator.application.query.generate.QueryGenerator;
import ir.mohaymen.querygenerator.application.query.generate.QueryGeneratorRequest;
import ir.mohaymen.querygenerator.application.query.generate.common.model.Query;
import ir.mohaymen.querygenerator.domain.having.Having;
import ir.mohaymen.querygenerator.domain.order.OrderByItem;
import ir.mohaymen.querygenerator.domain.schema.column.Column;
import ir.mohaymen.querygenerator.domain.schema.enumeration.DatePart;
import ir.mohaymen.querygenerator.domain.schema.table.Table;
import ir.mohaymen.querygenerator.domain.select.SelectColumn;
import ir.mohaymen.querygenerator.domain.select.SelectRaw;
import ir.mohaymen.querygenerator.domain.where.Where;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public final class Sql {

    private Sql() {
    }

    public static SelectQuery select() {
        return new SelectQuery(new SelectColumn(List.of()));
    }

    public static SelectQuery select(String... columns) {
        return new SelectQuery(new SelectColumn(Expressions.columns(columns)));
    }

    public static SelectQuery select(Column... columns) {
        return new SelectQuery(new SelectColumn(List.of(columns)));
    }

    public static SelectQuery selectRaw(String sql) {
        return new SelectQuery(new SelectRaw(sql));
    }

    public static SelectQuery selectRaw(String sql, Map<String, Object> parameters) {
        return new SelectQuery(new SelectRaw(sql, parameters));
    }

    public static InsertQuery insertInto(String table) {
        return insertInto(Expressions.table(table));
    }

    public static InsertQuery insertInto(String table, String alias) {
        return insertInto(Expressions.table(table, alias));
    }

    public static InsertQuery insertInto(Table table) {
        return new InsertQuery(table);
    }

    public static InsertAllQuery insertAll() {
        return new InsertAllQuery();
    }

    public static InsertAllQuery insertAll(InsertQuery first, InsertQuery... rest) {
        InsertAllQuery query = insertAll().row(first);
        for (InsertQuery row : rest) {
            query.row(row);
        }
        return query;
    }

    public static UpdateQuery update(String table) {
        return update(Expressions.table(table));
    }

    public static UpdateQuery update(String table, String alias) {
        return update(Expressions.table(table, alias));
    }

    public static UpdateQuery update(Table table) {
        return new UpdateQuery(table);
    }

    public static DeleteQuery deleteFrom(String table) {
        return deleteFrom(Expressions.table(table));
    }

    public static DeleteQuery deleteFrom(String table, String alias) {
        return deleteFrom(Expressions.table(table, alias));
    }

    public static DeleteQuery deleteFrom(Table table) {
        return new DeleteQuery(table);
    }

    public static Query generate(QueryGeneratorRequest request) {
        return QueryGenerator.oracle().generate(request);
    }

    public static Query generate(SqlStatement<?> statement) {
        return QueryGenerator.oracle().generate(statement.toRequest());
    }

    public static Column col(String name) {
        return Expressions.col(name);
    }

    public static Column col(String name, String alias) {
        return Expressions.col(name, alias);
    }

    public static Column jalali(String name) {
        return Expressions.jalali(name);
    }

    public static Column jalali(String name, String alias) {
        return Expressions.jalali(name, alias);
    }

    public static Column digits(String name) {
        return Expressions.digits(name);
    }

    public static Column digits(String name, String alias) {
        return Expressions.digits(name, alias);
    }

    public static Table table(String name) {
        return Expressions.table(name);
    }

    public static Table table(String name, String alias) {
        return Expressions.table(name, alias);
    }

    public static OrderByItem asc(String name) {
        return Expressions.asc(name);
    }

    public static OrderByItem desc(String name) {
        return Expressions.desc(name);
    }

    public static Where eq(String column, Object value) {
        return Predicates.eq(column, value);
    }

    public static Where ne(String column, Object value) {
        return Predicates.ne(column, value);
    }

    public static Where gt(String column, Object value) {
        return Predicates.gt(column, value);
    }

    public static Where ge(String column, Object value) {
        return Predicates.ge(column, value);
    }

    public static Where lt(String column, Object value) {
        return Predicates.lt(column, value);
    }

    public static Where le(String column, Object value) {
        return Predicates.le(column, value);
    }

    public static Where like(String column, Object value) {
        return Predicates.like(column, value);
    }

    public static Where notLike(String column, Object value) {
        return Predicates.notLike(column, value);
    }

    public static Where in(String column, Object... values) {
        return Predicates.in(column, values);
    }

    public static Where in(String column, Collection<?> values) {
        return Predicates.in(column, values);
    }

    public static Where notIn(String column, Object... values) {
        return Predicates.notIn(column, values);
    }

    public static Where between(String column, Object start, Object end) {
        return Predicates.between(column, start, end);
    }

    public static Where isNull(String column) {
        return Predicates.isNull(column);
    }

    public static Where notNull(String column) {
        return Predicates.notNull(column);
    }

    public static Where isTrue(String column) {
        return Predicates.isTrue(column);
    }

    public static Where isFalse(String column) {
        return Predicates.isFalse(column);
    }

    public static Where exists(String table, Where where) {
        return Predicates.exists(table, where);
    }

    public static Where exists(String table, String alias, Where where) {
        return Predicates.exists(table, alias, where);
    }

    public static Where datePart(String column, DatePart datePart, String operation, Object value) {
        return Predicates.datePart(column, datePart, operation, value);
    }

    public static Where raw(String sql) {
        return Predicates.raw(sql);
    }

    public static Where raw(String sql, Map<String, Object> parameters) {
        return Predicates.raw(sql, parameters);
    }

    public static Where and(Where... conditions) {
        return Predicates.and(conditions);
    }

    public static Where or(Where... conditions) {
        return Predicates.or(conditions);
    }

    public static Having having(String alias, String operation, Object value) {
        return Havings.alias(alias, operation, value);
    }

    public static Having havingRaw(String sql) {
        return Havings.raw(sql);
    }
}
