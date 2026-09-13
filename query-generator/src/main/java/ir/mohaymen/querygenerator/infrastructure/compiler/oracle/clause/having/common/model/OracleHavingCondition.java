package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.common.model;

public record OracleHavingCondition(String condition, boolean requiresWrapper) {

    public OracleHavingCondition {
        if (condition == null || condition.isBlank()) {
            throw new IllegalArgumentException("compiled having condition must not be null or blank");
        }
    }

}
