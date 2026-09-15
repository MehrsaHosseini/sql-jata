package ir.mohaymen.querygenerator.api.common.mapping;

import ir.mohaymen.querygenerator.application.query.builder.DeleteQuery;
import ir.mohaymen.querygenerator.application.query.builder.Havings;
import ir.mohaymen.querygenerator.application.query.builder.InsertAllQuery;
import ir.mohaymen.querygenerator.application.query.builder.InsertQuery;
import ir.mohaymen.querygenerator.application.query.builder.Predicates;
import ir.mohaymen.querygenerator.application.query.builder.SelectQuery;
import ir.mohaymen.querygenerator.application.query.builder.Sql;
import ir.mohaymen.querygenerator.application.query.builder.SqlStatement;
import ir.mohaymen.querygenerator.application.query.builder.UpdateQuery;
import ir.mohaymen.querygenerator.application.query.generate.common.model.SetOperator;
import ir.mohaymen.querygenerator.domain.having.Having;
import ir.mohaymen.querygenerator.domain.order.OrderByItem;
import ir.mohaymen.querygenerator.domain.parameter.ParameterMode;
import ir.mohaymen.querygenerator.domain.schema.column.Column;
import ir.mohaymen.querygenerator.domain.schema.enumeration.DisplayFormatting;
import ir.mohaymen.querygenerator.domain.schema.enumeration.JoinType;
import ir.mohaymen.querygenerator.domain.schema.enumeration.SortDirection;
import ir.mohaymen.querygenerator.domain.where.Where;
import ir.mohaymen.querygenerator.api.common.dto.ColumnRequest;
import ir.mohaymen.querygenerator.api.common.dto.DeleteQueryRequest;
import ir.mohaymen.querygenerator.api.common.dto.HavingRequest;
import ir.mohaymen.querygenerator.api.common.dto.InsertAllQueryRequest;
import ir.mohaymen.querygenerator.api.common.dto.InsertQueryRequest;
import ir.mohaymen.querygenerator.api.common.dto.JoinOnRequest;
import ir.mohaymen.querygenerator.api.common.dto.JoinRequest;
import ir.mohaymen.querygenerator.api.common.dto.OrderRequest;
import ir.mohaymen.querygenerator.api.common.dto.PredicateRequest;
import ir.mohaymen.querygenerator.api.common.dto.RawSqlRequest;
import ir.mohaymen.querygenerator.api.common.dto.SelectQueryRequest;
import ir.mohaymen.querygenerator.api.common.dto.SetOperationRequest;
import ir.mohaymen.querygenerator.api.common.dto.TableRequest;
import ir.mohaymen.querygenerator.api.common.dto.UpdateQueryRequest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

public class SqlRequestMapper {

    public SelectQuery toSelect(SelectQueryRequest request) {
        Objects.requireNonNull(request, "select request must not be null");
        if (request.columns() != null && request.selectRaw() != null) {
            throw new IllegalArgumentException("select cannot use both columns and selectRaw");
        }
        if (request.from() != null && request.fromRaw() != null) {
            throw new IllegalArgumentException("select cannot use both from and fromRaw");
        }
        if (request.groupBy() != null && request.groupByRaw() != null) {
            throw new IllegalArgumentException("select cannot use both groupBy and groupByRaw");
        }
        if (request.orderBy() != null && request.orderByRaw() != null) {
            throw new IllegalArgumentException("select cannot use both orderBy and orderByRaw");
        }
        if (hasPaginationConflict(request)) {
            throw new IllegalArgumentException("select cannot mix limit/offset with page/pageSize");
        }

        SelectQuery query = selectClause(request);
        applyFrom(query, request.from(), request.fromRaw());
        applyJoins(query, request.joins());
        if (request.where() != null) {
            query.where(toWhere(request.where()));
        }
        applyGroupBy(query, request.groupBy(), request.groupByRaw());
        if (request.having() != null) {
            query.having(toHaving(request.having()));
        }
        applyOrderBy(query, request.orderBy(), request.orderByRaw());
        applyPagination(query, request.limit(), request.offset(), request.page(), request.pageSize());
        applySetOperations(query, request.setOperations());
        return applyCommon(query, request.parameterMode(), request.bind());
    }

    public InsertQuery toInsert(InsertQueryRequest request) {
        Objects.requireNonNull(request, "insert request must not be null");
        InsertQuery query = insertClause(request);
        return applyCommon(query, request.parameterMode(), request.bind());
    }

    public InsertAllQuery toInsertAll(InsertAllQueryRequest request) {
        Objects.requireNonNull(request, "insert all request must not be null");
        if (request.rows() == null || request.rows().isEmpty()) {
            throw new IllegalArgumentException("insert all needs at least one row");
        }
        InsertAllQuery query = Sql.insertAll();
        for (InsertQueryRequest row : request.rows()) {
            query.row(insertClause(row));
        }
        return applyCommon(query, request.parameterMode(), request.bind());
    }

    public UpdateQuery toUpdate(UpdateQueryRequest request) {
        Objects.requireNonNull(request, "update request must not be null");
        if (request.table() == null || request.table().isBlank()) {
            throw new IllegalArgumentException("update table must not be blank");
        }
        if (request.set() == null || request.set().isEmpty()) {
            throw new IllegalArgumentException("update needs at least one assignment");
        }
        UpdateQuery query = Sql.update(request.table(), request.alias());
        for (Map.Entry<String, Object> assignment : request.set().entrySet()) {
            query.set(assignment.getKey(), assignment.getValue());
        }
        if (request.where() != null) {
            query.where(toWhere(request.where()));
        }
        return applyCommon(query, request.parameterMode(), request.bind());
    }

    public DeleteQuery toDelete(DeleteQueryRequest request) {
        Objects.requireNonNull(request, "delete request must not be null");
        if (request.table() == null || request.table().isBlank()) {
            throw new IllegalArgumentException("delete table must not be blank");
        }
        DeleteQuery query = Sql.deleteFrom(request.table(), request.alias());
        if (request.where() != null) {
            query.where(toWhere(request.where()));
        }
        return applyCommon(query, request.parameterMode(), request.bind());
    }

    Where toWhere(PredicateRequest request) {
        Objects.requireNonNull(request, "predicate must not be null");
        String op = normalizeOp(request.op());
        return switch (op) {
            case "eq", "=" -> Predicates.eq(requireColumn(request), request.value());
            case "ne", "!=", "<>" -> Predicates.ne(requireColumn(request), request.value());
            case "gt", ">" -> Predicates.gt(requireColumn(request), request.value());
            case "ge", ">=" -> Predicates.ge(requireColumn(request), request.value());
            case "lt", "<" -> Predicates.lt(requireColumn(request), request.value());
            case "le", "<=" -> Predicates.le(requireColumn(request), request.value());
            case "like" -> Predicates.like(requireColumn(request), request.value());
            case "notlike", "not like" -> Predicates.notLike(requireColumn(request), request.value());
            case "in" -> Predicates.in(requireColumn(request), inValues(request));
            case "notin", "not in" -> Predicates.notIn(requireColumn(request), inValues(request));
            case "between" -> Predicates.between(requireColumn(request), request.start(), request.end());
            case "isnull", "is null" -> Predicates.isNull(requireColumn(request));
            case "notnull", "not null", "isnotnull", "is not null" -> Predicates.notNull(requireColumn(request));
            case "istrue", "is true" -> Predicates.isTrue(requireColumn(request));
            case "isfalse", "is false" -> Predicates.isFalse(requireColumn(request));
            case "exists" -> exists(request);
            case "datepart" -> Predicates.datePart(
                    requireColumn(request),
                    Objects.requireNonNull(request.datePart(), "datePart must not be null"),
                    requireText(request.operation(), "predicate operation"),
                    request.value());
            case "raw" -> request.parameters() == null
                    ? Predicates.raw(requireText(request.sql(), "raw sql"))
                    : Predicates.raw(requireText(request.sql(), "raw sql"), request.parameters());
            case "and" -> Predicates.and(toWhereArray(request.conditions()));
            case "or" -> Predicates.or(toWhereArray(request.conditions()));
            default -> throw new IllegalArgumentException("unknown predicate op: " + request.op());
        };
    }

    private SelectQuery selectClause(SelectQueryRequest request) {
        if (request.selectRaw() != null) {
            RawSqlRequest raw = request.selectRaw();
            return raw.parameters() == null
                    ? Sql.selectRaw(requireText(raw.sql(), "selectRaw.sql"))
                    : Sql.selectRaw(requireText(raw.sql(), "selectRaw.sql"), raw.parameters());
        }
        if (request.columns() == null || request.columns().isEmpty()) {
            return Sql.select();
        }
        Column[] columns = new Column[request.columns().size()];
        for (int index = 0; index < request.columns().size(); index++) {
            columns[index] = toColumn(request.columns().get(index));
        }
        return Sql.select(columns);
    }

    private InsertQuery insertClause(InsertQueryRequest request) {
        if (request.table() == null || request.table().isBlank()) {
            throw new IllegalArgumentException("insert table must not be blank");
        }
        InsertQuery query = Sql.insertInto(request.table(), request.alias());
        if (request.columns() != null && !request.columns().isEmpty()) {
            query.columns(request.columns().toArray(String[]::new));
        }
        if (request.values() == null) {
            throw new IllegalArgumentException("insert values must not be empty");
        }
        return query.values(request.values().toArray());
    }

    private void applyFrom(SelectQuery query, TableRequest from, RawSqlRequest fromRaw) {
        if (fromRaw != null) {
            if (fromRaw.parameters() == null) {
                query.fromRaw(requireText(fromRaw.sql(), "fromRaw.sql"));
            } else {
                query.fromRaw(requireText(fromRaw.sql(), "fromRaw.sql"), fromRaw.parameters());
            }
            return;
        }
        if (from != null) {
            query.from(requireText(from.table(), "from.table"), from.alias());
        }
    }

    private void applyJoins(SelectQuery query, List<JoinRequest> joins) {
        if (joins == null || joins.isEmpty()) {
            return;
        }
        for (JoinRequest join : joins) {
            Objects.requireNonNull(join, "join must not be null");
            RawSqlRequest source = joinSource(join);
            if (source != null) {
                query.joinRaw(typedRawJoin(join, source.sql()), joinParameters(join, source));
                continue;
            }
            if (join.getRaw() != null && !join.getRaw().isBlank()) {
                query.joinRaw(join.getRaw(), join.getParameters() == null ? Map.of() : join.getParameters());
                continue;
            }
            JoinType type = join.getType() == null ? JoinType.INNER : join.getType();
            if (type == JoinType.CROSS) {
                query.crossJoin(requireText(join.getTable(), "join.table"), join.getAlias());
                continue;
            }
            startJoin(query, type, requireText(join.getTable(), "join.table"), join.getAlias());
            if (join.getOn() == null || join.getOn().isEmpty()) {
                throw new IllegalArgumentException(type + " join needs at least one on condition");
            }
            for (JoinOnRequest on : join.getOn()) {
                Objects.requireNonNull(on, "join on must not be null");
                String operation = on.operation() == null || on.operation().isBlank() ? "=" : on.operation();
                query.on(requireText(on.left(), "join on.left"), operation, requireText(on.right(), "join on.right"));
            }
        }
    }

    private static void startJoin(SelectQuery query, JoinType type, String table, String alias) {
        switch (type) {
            case INNER -> query.innerJoin(table, alias);
            case LEFT -> query.leftJoin(table, alias);
            case RIGHT -> query.rightJoin(table, alias);
            case FULL -> query.fullJoin(table, alias);
            case CROSS -> query.crossJoin(table, alias);
        }
    }

    private static String typedRawJoin(JoinRequest join, String source) {
        JoinType type = join.getType() == null ? JoinType.INNER : join.getType();
        String keyword = joinKeyword(type);
        if (type == JoinType.CROSS) {
            if (join.getOn() != null && !join.getOn().isEmpty()) {
                throw new IllegalArgumentException("CROSS join must not have an on condition");
            }
            return keyword + " " + source;
        }
        if (join.getOn() == null || join.getOn().isEmpty()) {
            throw new IllegalArgumentException(type + " join needs at least one on condition");
        }
        StringJoiner conditions = new StringJoiner(" AND ");
        for (JoinOnRequest on : join.getOn()) {
            Objects.requireNonNull(on, "join on must not be null");
            String operation = on.operation() == null || on.operation().isBlank() ? "=" : on.operation().trim();
            conditions.add(quoteColumn(requireText(on.left(), "join on.left"))
                    + " " + operation + " "
                    + quoteColumn(requireText(on.right(), "join on.right")));
        }
        return keyword + " " + source + " ON " + conditions;
    }

    private static RawSqlRequest joinSource(JoinRequest join) {
        if (join.getFromRaw() != null && join.getFromRaw().sql() != null && !join.getFromRaw().sql().isBlank()) {
            return join.getFromRaw();
        }
        if ((join.getTable() == null || join.getTable().isBlank())
                && join.getType() != null
                && join.getRaw() != null
                && !join.getRaw().isBlank()
                && !looksLikeFullJoinClause(join.getRaw())) {
            return new RawSqlRequest(join.getRaw(), join.getParameters());
        }
        return null;
    }

    private static boolean looksLikeFullJoinClause(String raw) {
        String trimmed = raw.trim();
        String upper = trimmed.toUpperCase(Locale.ROOT);
        return upper.startsWith("INNER JOIN")
                || upper.startsWith("LEFT ")
                || upper.startsWith("RIGHT ")
                || upper.startsWith("FULL ")
                || upper.startsWith("CROSS JOIN");
    }

    private static Map<String, Object> joinParameters(JoinRequest join, RawSqlRequest source) {
        Map<String, Object> parameters = new LinkedHashMap<>();
        if (source != null && source.parameters() != null) {
            parameters.putAll(source.parameters());
        }
        if (join.getParameters() != null) {
            parameters.putAll(join.getParameters());
        }
        return parameters;
    }

    private static String joinKeyword(JoinType type) {
        return switch (type) {
            case INNER -> "INNER JOIN";
            case LEFT -> "LEFT OUTER JOIN";
            case RIGHT -> "RIGHT OUTER JOIN";
            case FULL -> "FULL OUTER JOIN";
            case CROSS -> "CROSS JOIN";
        };
    }

    private static String quoteColumn(String name) {
        StringJoiner quoted = new StringJoiner(".");
        for (String part : name.trim().split("\\.", -1)) {
            String bare = part.trim();
            if (bare.length() > 1 && bare.startsWith("\"") && bare.endsWith("\"")) {
                bare = bare.substring(1, bare.length() - 1);
            }
            if (bare.isBlank()) {
                throw new IllegalArgumentException("join column must not be blank");
            }
            quoted.add("\"" + bare + "\"");
        }
        return quoted.toString();
    }

    private void applyGroupBy(SelectQuery query, List<String> groupBy, RawSqlRequest groupByRaw) {
        if (groupByRaw != null) {
            if (groupByRaw.parameters() == null) {
                query.groupByRaw(requireText(groupByRaw.sql(), "groupByRaw.sql"));
            } else {
                query.groupByRaw(requireText(groupByRaw.sql(), "groupByRaw.sql"), groupByRaw.parameters());
            }
            return;
        }
        if (groupBy != null && !groupBy.isEmpty()) {
            query.groupBy(groupBy.toArray(String[]::new));
        }
    }

    private void applyOrderBy(SelectQuery query, List<OrderRequest> orderBy, RawSqlRequest orderByRaw) {
        if (orderByRaw != null) {
            if (orderByRaw.parameters() == null) {
                query.orderByRaw(requireText(orderByRaw.sql(), "orderByRaw.sql"));
            } else {
                query.orderByRaw(requireText(orderByRaw.sql(), "orderByRaw.sql"), orderByRaw.parameters());
            }
            return;
        }
        if (orderBy == null || orderBy.isEmpty()) {
            return;
        }
        List<OrderByItem> items = new ArrayList<>(orderBy.size());
        for (OrderRequest order : orderBy) {
            Objects.requireNonNull(order, "order by item must not be null");
            String column = requireText(order.column(), "orderBy.column");
            SortDirection direction = order.direction();
            items.add(direction == null ? new OrderByItem(new Column(column)) : new OrderByItem(new Column(column), direction));
        }
        query.orderBy(items.toArray(OrderByItem[]::new));
    }

    private static void applyPagination(SelectQuery query, Integer limit, Integer offset, Integer page, Integer pageSize) {
        if (page != null || pageSize != null) {
            if (page == null || pageSize == null) {
                throw new IllegalArgumentException("page and pageSize must be provided together");
            }
            query.page(page, pageSize);
            return;
        }
        if (offset != null) {
            query.offset(offset);
        }
        if (limit != null) {
            query.limit(limit);
        }
    }

    private void applySetOperations(SelectQuery query, List<SetOperationRequest> setOperations) {
        if (setOperations == null || setOperations.isEmpty()) {
            return;
        }
        for (SetOperationRequest setOperation : setOperations) {
            Objects.requireNonNull(setOperation, "set operation must not be null");
            SetOperator operator = Objects.requireNonNull(setOperation.operator(), "set operator must not be null");
            SelectQuery right = toSelect(Objects.requireNonNull(setOperation.query(), "set operation query must not be null"));
            switch (operator) {
                case UNION -> query.union(right);
                case UNION_ALL -> query.unionAll(right);
                case INTERSECT -> query.intersect(right);
                case MINUS -> query.minus(right);
            }
        }
    }

    private Having toHaving(HavingRequest request) {
        Objects.requireNonNull(request, "having must not be null");
        String op = request.op() == null ? "alias" : normalizeOp(request.op());
        return switch (op) {
            case "alias" -> Havings.alias(
                    requireText(request.alias(), "having.alias"),
                    requireText(request.operation(), "having.operation"),
                    request.value());
            case "raw" -> request.parameters() == null
                    ? Havings.raw(requireText(request.sql(), "having.sql"))
                    : Havings.raw(requireText(request.sql(), "having.sql"), request.parameters());
            case "and" -> Havings.and(toHavingArray(request.conditions()));
            case "or" -> Havings.or(toHavingArray(request.conditions()));
            default -> throw new IllegalArgumentException("unknown having op: " + request.op());
        };
    }

    private Where exists(PredicateRequest request) {
        String table = requireText(request.table(), "exists.table");
        Where where = toWhere(Objects.requireNonNull(request.where(), "exists.where must not be null"));
        return request.alias() == null ? Predicates.exists(table, where) : Predicates.exists(table, request.alias(), where);
    }

    private Where[] toWhereArray(List<PredicateRequest> conditions) {
        if (conditions == null || conditions.isEmpty()) {
            throw new IllegalArgumentException("compound predicate needs at least one condition");
        }
        Where[] where = new Where[conditions.size()];
        for (int index = 0; index < conditions.size(); index++) {
            where[index] = toWhere(conditions.get(index));
        }
        return where;
    }

    private Having[] toHavingArray(List<HavingRequest> conditions) {
        if (conditions == null || conditions.isEmpty()) {
            throw new IllegalArgumentException("compound having needs at least one condition");
        }
        Having[] having = new Having[conditions.size()];
        for (int index = 0; index < conditions.size(); index++) {
            having[index] = toHaving(conditions.get(index));
        }
        return having;
    }

    private static Column toColumn(ColumnRequest request) {
        Objects.requireNonNull(request, "column must not be null");
        String name = requireText(request.name(), "column.name");
        DisplayFormatting format = request.format();
        if (format == DisplayFormatting.TO_JALALI) {
            return request.alias() == null ? Sql.jalali(name) : Sql.jalali(name, request.alias());
        }
        if (format == DisplayFormatting.SEPARATED_DIGITS) {
            return request.alias() == null ? Sql.digits(name) : Sql.digits(name, request.alias());
        }
        return request.alias() == null ? Sql.col(name) : Sql.col(name, request.alias());
    }

    private static List<Object> inValues(PredicateRequest request) {
        if (request.values() != null) {
            return request.values();
        }
        if (request.value() instanceof List<?> list) {
            return new ArrayList<>(list);
        }
        if (request.value() != null) {
            return List.of(request.value());
        }
        throw new IllegalArgumentException("in predicate needs values");
    }

    private static <T extends SqlStatement<T>> T applyCommon(T statement, ParameterMode parameterMode, Map<String, Object> bind) {
        if (parameterMode == ParameterMode.INLINE) {
            statement.inline();
        } else {
            statement.named();
        }
        if (bind != null && !bind.isEmpty()) {
            statement.bind(bind);
        }
        return statement;
    }

    private static boolean hasPaginationConflict(SelectQueryRequest request) {
        boolean window = request.limit() != null || request.offset() != null;
        boolean page = request.page() != null || request.pageSize() != null;
        return window && page;
    }

    private static String requireColumn(PredicateRequest request) {
        return requireText(request.column(), "predicate.column");
    }

    private static String requireText(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }

    private static String normalizeOp(String op) {
        if (op == null || op.isBlank()) {
            throw new IllegalArgumentException("predicate op must not be blank");
        }
        return op.trim().toLowerCase(Locale.ROOT).replace('_', ' ').replaceAll("\\s+", " ");
    }
}
