package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.model;


public record OracleJoinSyntax(String keyword, boolean supportsCondition) {

    public OracleJoinSyntax {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("join keyword must not be null or blank");
        }
    }

}
