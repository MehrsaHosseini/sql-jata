package ir.mohaymen.querygenerator.application.query.generate.api;

import ir.mohaymen.querygenerator.domain.having.Having;
import ir.mohaymen.querygenerator.domain.having.HavingCondition;
import ir.mohaymen.querygenerator.domain.having.HavingRaw;
import ir.mohaymen.querygenerator.domain.having.MultipleHaving;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class Havings {

    private Havings() {
    }

    public static Having alias(String alias, String operation, Object value) {
        return new HavingCondition(alias, operation, value);
    }

    public static Having raw(String sql) {
        return new HavingRaw(sql);
    }

    public static Having raw(String sql, Map<String, Object> parameters) {
        return new HavingRaw(sql, parameters);
    }

    public static Having and(Having... conditions) {
        List<Having> operand = requireConditions(conditions);
        if (operand.size() == 1) {
            return operand.getFirst();
        }
        return new MultipleHaving(operand, null);
    }

    public static Having or(Having... conditions) {
        List<Having> operand = requireConditions(conditions);
        Having combined = operand.getLast();
        for (int index = operand.size() - 2; index >= 0; index--) {
            combined = new MultipleHaving(List.of(operand.get(index)), List.of(combined));
        }
        return combined;
    }

    private static List<Having> requireConditions(Having... conditions) {
        if (conditions == null || conditions.length == 0) {
            throw new IllegalArgumentException("at least one having condition is required");
        }
        List<Having> operand = new ArrayList<>(conditions.length);
        for (Having condition : conditions) {
            operand.add(Objects.requireNonNull(condition, "having condition must not be null"));
        }
        return operand;
    }
}
