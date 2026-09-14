package ir.mohaymen.querygenerator.application.query.builder;

import ir.mohaymen.querygenerator.domain.schema.column.Column;
import ir.mohaymen.querygenerator.domain.schema.enumeration.DatePart;
import ir.mohaymen.querygenerator.domain.schema.table.Table;
import ir.mohaymen.querygenerator.domain.where.BasicWhere;
import ir.mohaymen.querygenerator.domain.where.MultipleWhere;
import ir.mohaymen.querygenerator.domain.where.Where;
import ir.mohaymen.querygenerator.domain.where.WhereDatePart;
import ir.mohaymen.querygenerator.domain.where.WhereExists;
import ir.mohaymen.querygenerator.domain.where.WhereNull;
import ir.mohaymen.querygenerator.domain.where.WhereRaw;
import ir.mohaymen.querygenerator.domain.where.WhereTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class Predicates {

    private Predicates() {
    }

    public static Where eq(String column, Object value) {
        return compare(column, "=", value);
    }

    public static Where eq(Column column, Object value) {
        return compare(column, "=", value);
    }

    public static Where ne(String column, Object value) {
        return compare(column, "<>", value);
    }

    public static Where ne(Column column, Object value) {
        return compare(column, "<>", value);
    }

    public static Where gt(String column, Object value) {
        return compare(column, ">", value);
    }

    public static Where gt(Column column, Object value) {
        return compare(column, ">", value);
    }

    public static Where ge(String column, Object value) {
        return compare(column, ">=", value);
    }

    public static Where ge(Column column, Object value) {
        return compare(column, ">=", value);
    }

    public static Where lt(String column, Object value) {
        return compare(column, "<", value);
    }

    public static Where lt(Column column, Object value) {
        return compare(column, "<", value);
    }

    public static Where le(String column, Object value) {
        return compare(column, "<=", value);
    }

    public static Where le(Column column, Object value) {
        return compare(column, "<=", value);
    }

    public static Where like(String column, Object value) {
        return compare(column, "LIKE", value);
    }

    public static Where like(Column column, Object value) {
        return compare(column, "LIKE", value);
    }

    public static Where notLike(String column, Object value) {
        return compare(column, "NOT LIKE", value);
    }

    public static Where notLike(Column column, Object value) {
        return compare(column, "NOT LIKE", value);
    }

    public static Where in(String column, Object... values) {
        return in(Expressions.col(column), values);
    }

    public static Where in(Column column, Object... values) {
        if (values != null && values.length == 1 && values[0] instanceof Collection<?> collection) {
            return in(column, collection);
        }
        return compare(column, "IN", values);
    }

    public static Where in(String column, Collection<?> values) {
        return in(Expressions.col(column), values);
    }

    public static Where in(Column column, Collection<?> values) {
        return compare(column, "IN", List.copyOf(values));
    }

    public static Where notIn(String column, Object... values) {
        return notIn(Expressions.col(column), values);
    }

    public static Where notIn(Column column, Object... values) {
        if (values != null && values.length == 1 && values[0] instanceof Collection<?> collection) {
            return notIn(column, collection);
        }
        return compare(column, "NOT IN", values);
    }

    public static Where notIn(String column, Collection<?> values) {
        return notIn(Expressions.col(column), values);
    }

    public static Where notIn(Column column, Collection<?> values) {
        return compare(column, "NOT IN", List.copyOf(values));
    }

    public static Where between(String column, Object start, Object end) {
        return between(Expressions.col(column), start, end);
    }

    public static Where between(Column column, Object start, Object end) {
        return compare(column, "BETWEEN", List.of(start, end));
    }

    public static Where isNull(String column) {
        return isNull(Expressions.col(column));
    }

    public static Where isNull(Column column) {
        return new WhereNull(column, true);
    }

    public static Where notNull(String column) {
        return notNull(Expressions.col(column));
    }

    public static Where notNull(Column column) {
        return new WhereNull(column, false);
    }

    public static Where isTrue(String column) {
        return isTrue(Expressions.col(column));
    }

    public static Where isTrue(Column column) {
        return new WhereTrue(column, true);
    }

    public static Where isFalse(String column) {
        return isFalse(Expressions.col(column));
    }

    public static Where isFalse(Column column) {
        return new WhereTrue(column, false);
    }

    public static Where exists(String table, Where where) {
        return exists(Expressions.table(table), where);
    }

    public static Where exists(String table, String alias, Where where) {
        return exists(Expressions.table(table, alias), where);
    }

    public static Where exists(Table table, Where where) {
        return new WhereExists(table, where);
    }

    public static Where datePart(String column, DatePart datePart, String operation, Object value) {
        return datePart(Expressions.col(column), datePart, operation, value);
    }

    public static Where datePart(Column column, DatePart datePart, String operation, Object value) {
        return new WhereDatePart(column, datePart, operation, value);
    }

    public static Where raw(String sql) {
        return new WhereRaw(sql);
    }

    public static Where raw(String sql, Map<String, Object> parameters) {
        return new WhereRaw(sql, parameters);
    }

    public static Where and(Where... conditions) {
        List<Where> operand = requireConditions(conditions);
        if (operand.size() == 1) {
            return operand.getFirst();
        }
        return new MultipleWhere(operand, null);
    }

    public static Where or(Where... conditions) {
        List<Where> operand = requireConditions(conditions);
        Where combined = operand.getLast();
        for (int index = operand.size() - 2; index >= 0; index--) {
            combined = new MultipleWhere(List.of(operand.get(index)), List.of(combined));
        }
        return combined;
    }

    private static Where compare(String column, String operation, Object value) {
        return compare(Expressions.col(column), operation, value);
    }

    private static Where compare(Column column, String operation, Object value) {
        return new BasicWhere(Objects.requireNonNull(column, "column must not be null"), operation, value);
    }

    private static List<Where> requireConditions(Where... conditions) {
        if (conditions == null || conditions.length == 0) {
            throw new IllegalArgumentException("at least one condition is required");
        }
        List<Where> operand = new ArrayList<>(conditions.length);
        for (Where condition : conditions) {
            operand.add(Objects.requireNonNull(condition, "condition must not be null"));
        }
        return operand;
    }
}
