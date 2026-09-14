package ir.mohaymen.querygenerator.application.query.generate.api;

import ir.mohaymen.querygenerator.domain.order.OrderByItem;
import ir.mohaymen.querygenerator.domain.schema.column.Column;
import ir.mohaymen.querygenerator.domain.schema.enumeration.DisplayFormatting;
import ir.mohaymen.querygenerator.domain.schema.enumeration.SortDirection;
import ir.mohaymen.querygenerator.domain.schema.table.Table;

import java.util.ArrayList;
import java.util.List;

public final class Expressions {

    private Expressions() {
    }

    public static Column col(String name) {
        return new Column(name);
    }

    public static Column col(String name, String alias) {
        return new Column(name, alias);
    }

    public static Column jalali(String name) {
        return new Column(name, null, DisplayFormatting.TO_JALALI);
    }

    public static Column jalali(String name, String alias) {
        return new Column(name, alias, DisplayFormatting.TO_JALALI);
    }

    public static Column digits(String name) {
        return new Column(name, null, DisplayFormatting.SEPARATED_DIGITS);
    }

    public static Column digits(String name, String alias) {
        return new Column(name, alias, DisplayFormatting.SEPARATED_DIGITS);
    }

    public static Table table(String name) {
        return new Table(name);
    }

    public static Table table(String name, String alias) {
        return new Table(name, alias);
    }

    public static OrderByItem asc(String name) {
        return new OrderByItem(col(name), SortDirection.ASC);
    }

    public static OrderByItem desc(String name) {
        return new OrderByItem(col(name), SortDirection.DESC);
    }

    public static OrderByItem asc(Column column) {
        return new OrderByItem(column, SortDirection.ASC);
    }

    public static OrderByItem desc(Column column) {
        return new OrderByItem(column, SortDirection.DESC);
    }

    static List<Column> columns(String... names) {
        List<Column> columns = new ArrayList<>(names.length);
        for (String name : names) {
            columns.add(col(name));
        }
        return columns;
    }
}
