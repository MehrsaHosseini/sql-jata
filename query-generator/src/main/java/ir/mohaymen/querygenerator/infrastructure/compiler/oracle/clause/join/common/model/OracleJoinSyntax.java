package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.model;

/**
 * Oracle rendering rules of a single join type, where a cross join is the only one that is written
 * without an on condition.
 */
public record OracleJoinSyntax(String keyword, boolean supportsCondition) {

    public OracleJoinSyntax {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("join keyword must not be null or blank");
        }
    }

}
