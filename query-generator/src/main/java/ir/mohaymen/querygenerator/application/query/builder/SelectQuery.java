package ir.mohaymen.querygenerator.application.query.builder;

import ir.mohaymen.querygenerator.application.query.generate.QueryGeneratorRequest;
import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.application.query.generate.common.model.QuerySetOperation;
import ir.mohaymen.querygenerator.application.query.generate.common.model.SetOperator;
import ir.mohaymen.querygenerator.domain.from.From;
import ir.mohaymen.querygenerator.domain.from.FromRaw;
import ir.mohaymen.querygenerator.domain.from.FromTable;
import ir.mohaymen.querygenerator.domain.group.GroupBy;
import ir.mohaymen.querygenerator.domain.group.GroupByColumn;
import ir.mohaymen.querygenerator.domain.group.GroupByRaw;
import ir.mohaymen.querygenerator.domain.having.Having;
import ir.mohaymen.querygenerator.domain.join.Join;
import ir.mohaymen.querygenerator.domain.join.JoinItem;
import ir.mohaymen.querygenerator.domain.join.JoinList;
import ir.mohaymen.querygenerator.domain.join.JoinOn;
import ir.mohaymen.querygenerator.domain.join.JoinRaw;
import ir.mohaymen.querygenerator.domain.limit_offset.Limit_offset;
import ir.mohaymen.querygenerator.domain.limit_offset.Page_size;
import ir.mohaymen.querygenerator.domain.limit_offset.Pagination;
import ir.mohaymen.querygenerator.domain.order.OrderBy;
import ir.mohaymen.querygenerator.domain.order.OrderByColumn;
import ir.mohaymen.querygenerator.domain.order.OrderByItem;
import ir.mohaymen.querygenerator.domain.order.OrderByRaw;
import ir.mohaymen.querygenerator.domain.schema.column.Column;
import ir.mohaymen.querygenerator.domain.schema.enumeration.JoinType;
import ir.mohaymen.querygenerator.domain.schema.table.Table;
import ir.mohaymen.querygenerator.domain.select.Select;
import ir.mohaymen.querygenerator.domain.where.Where;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class SelectQuery extends SqlStatement<SelectQuery> {

    private final Select select;
    private From from;
    private final List<Join> joins = new ArrayList<>();
    private PendingJoin pendingJoin;
    private Where where;
    private GroupBy groupBy;
    private Having having;
    private OrderBy orderBy;
    private Pagination pagination;
    private final List<QuerySetOperation> setOperations = new ArrayList<>();

    SelectQuery(Select select) {
        this.select = select;
    }

    public SelectQuery from(String table) {
        return from(Expressions.table(table));
    }

    public SelectQuery from(String table, String alias) {
        return from(Expressions.table(table, alias));
    }

    public SelectQuery from(Table table) {
        flushJoin();
        this.from = new FromTable(Objects.requireNonNull(table, "table must not be null"));
        return this;
    }

    public SelectQuery fromRaw(String sql) {
        return fromRaw(sql, Map.of());
    }

    public SelectQuery fromRaw(String sql, Map<String, Object> parameters) {
        flushJoin();
        this.from = new FromRaw(sql, parameters);
        return this;
    }

    public SelectQuery innerJoin(String table) {
        return innerJoin(table, null);
    }

    public SelectQuery innerJoin(String table, String alias) {
        return startJoin(JoinType.INNER, table, alias);
    }

    public SelectQuery leftJoin(String table) {
        return leftJoin(table, null);
    }

    public SelectQuery leftJoin(String table, String alias) {
        return startJoin(JoinType.LEFT, table, alias);
    }

    public SelectQuery rightJoin(String table) {
        return rightJoin(table, null);
    }

    public SelectQuery rightJoin(String table, String alias) {
        return startJoin(JoinType.RIGHT, table, alias);
    }

    public SelectQuery fullJoin(String table) {
        return fullJoin(table, null);
    }

    public SelectQuery fullJoin(String table, String alias) {
        return startJoin(JoinType.FULL, table, alias);
    }

    public SelectQuery crossJoin(String table) {
        return crossJoin(table, null);
    }

    public SelectQuery crossJoin(String table, String alias) {
        flushJoin();
        joins.add(new JoinItem(JoinType.CROSS, Expressions.table(table, alias)));
        return this;
    }

    public SelectQuery on(String left, String right) {
        return on(left, "=", right);
    }

    public SelectQuery on(String left, String operation, String right) {
        if (pendingJoin == null) {
            throw new IllegalStateException("on() requires a join");
        }
        pendingJoin.ons.add(new JoinOn(Expressions.col(left), operation, Expressions.col(right)));
        return this;
    }

    public SelectQuery joinRaw(String sql) {
        return joinRaw(sql, Map.of());
    }

    public SelectQuery joinRaw(String sql, Map<String, Object> parameters) {
        flushJoin();
        joins.add(new JoinRaw(sql, parameters));
        return this;
    }

    public SelectQuery where(Where condition) {
        flushJoin();
        this.where = this.where == null ? condition : Predicates.and(this.where, condition);
        return this;
    }

    public SelectQuery groupBy(String... columns) {
        flushJoin();
        appendGroupBy(new GroupByColumn(Expressions.columns(columns)));
        return this;
    }

    public SelectQuery groupBy(Column... columns) {
        flushJoin();
        appendGroupBy(new GroupByColumn(List.of(columns)));
        return this;
    }

    public SelectQuery groupByRaw(String sql) {
        return groupByRaw(sql, Map.of());
    }

    public SelectQuery groupByRaw(String sql, Map<String, Object> parameters) {
        flushJoin();
        this.groupBy = new GroupByRaw(sql, parameters);
        return this;
    }

    public SelectQuery having(Having condition) {
        flushJoin();
        this.having = this.having == null ? condition : Havings.and(this.having, condition);
        return this;
    }

    public SelectQuery having(String alias, String operation, Object value) {
        return having(Havings.alias(alias, operation, value));
    }

    public SelectQuery havingRaw(String sql) {
        return havingRaw(sql, Map.of());
    }

    public SelectQuery havingRaw(String sql, Map<String, Object> parameters) {
        return having(Havings.raw(sql, parameters));
    }

    public SelectQuery orderBy(String... columns) {
        flushJoin();
        List<OrderByItem> items = new ArrayList<>(columns.length);
        for (String column : columns) {
            items.add(new OrderByItem(Expressions.col(column)));
        }
        appendOrderBy(items);
        return this;
    }

    public SelectQuery orderBy(OrderByItem... items) {
        flushJoin();
        appendOrderBy(List.of(items));
        return this;
    }

    public SelectQuery orderByRaw(String sql) {
        return orderByRaw(sql, Map.of());
    }

    public SelectQuery orderByRaw(String sql, Map<String, Object> parameters) {
        flushJoin();
        this.orderBy = new OrderByRaw(sql, parameters);
        return this;
    }

    public SelectQuery limit(int limit) {
        flushJoin();
        int offset = this.pagination instanceof Limit_offset limitOffset && limitOffset.offset() != null
                ? limitOffset.offset()
                : 0;
        this.pagination = new Limit_offset(offset, limit);
        return this;
    }

    public SelectQuery offset(int offset) {
        flushJoin();
        Integer limit = this.pagination instanceof Limit_offset limitOffset ? limitOffset.limit() : null;
        this.pagination = new Limit_offset(offset, limit);
        return this;
    }

    public SelectQuery page(int page, int pageSize) {
        flushJoin();
        this.pagination = new Page_size(page, pageSize);
        return this;
    }

    public SelectQuery union(SelectQuery other) {
        return addSetOperation(SetOperator.UNION, other);
    }

    public SelectQuery unionAll(SelectQuery other) {
        return addSetOperation(SetOperator.UNION_ALL, other);
    }

    public SelectQuery intersect(SelectQuery other) {
        return addSetOperation(SetOperator.INTERSECT, other);
    }

    public SelectQuery minus(SelectQuery other) {
        return addSetOperation(SetOperator.MINUS, other);
    }

    public QueryContext toContext() {
        flushJoin();
        return applyBindings(new QueryContext(select, from, join(), where, groupBy, having, orderBy, pagination));
    }

    @Override
    public QueryGeneratorRequest toRequest() {
        return new QueryGeneratorRequest(toContext(), List.copyOf(setOperations));
    }

    private SelectQuery addSetOperation(SetOperator operator, SelectQuery other) {
        flushJoin();
        Objects.requireNonNull(other, "set operand must not be null");
        QueryGeneratorRequest right = other.toRequest();
        setOperations.add(new QuerySetOperation(operator, right.queryContext(), right.setOperations()));
        return this;
    }

    private SelectQuery startJoin(JoinType joinType, String table, String alias) {
        flushJoin();
        pendingJoin = new PendingJoin(joinType, Expressions.table(table, alias));
        return this;
    }

    private void flushJoin() {
        if (pendingJoin == null) {
            return;
        }
        if (pendingJoin.ons.isEmpty()) {
            throw new IllegalStateException(pendingJoin.joinType + " join needs at least one on condition");
        }
        joins.add(new JoinItem(pendingJoin.joinType, pendingJoin.table, List.copyOf(pendingJoin.ons)));
        pendingJoin = null;
    }

    private Join join() {
        if (joins.isEmpty()) {
            return null;
        }
        if (joins.size() == 1) {
            return joins.getFirst();
        }
        return new JoinList(List.copyOf(joins));
    }

    private void appendGroupBy(GroupByColumn extra) {
        if (this.groupBy instanceof GroupByColumn existing && existing.columnList() != null) {
            List<Column> merged = new ArrayList<>(existing.columnList());
            merged.addAll(extra.columnList());
            this.groupBy = new GroupByColumn(merged);
            return;
        }
        this.groupBy = extra;
    }

    private void appendOrderBy(List<OrderByItem> extra) {
        if (this.orderBy instanceof OrderByColumn existing && existing.columnList() != null) {
            List<OrderByItem> merged = new ArrayList<>(existing.columnList());
            merged.addAll(extra);
            this.orderBy = new OrderByColumn(merged);
            return;
        }
        this.orderBy = new OrderByColumn(new ArrayList<>(extra));
    }

    private static final class PendingJoin {
        private final JoinType joinType;
        private final Table table;
        private final List<JoinOn> ons = new ArrayList<>();

        private PendingJoin(JoinType joinType, Table table) {
            this.joinType = joinType;
            this.table = table;
        }
    }
}
