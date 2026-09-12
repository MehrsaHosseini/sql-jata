package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.common.identifier;

public interface OracleIdentifierQuoter {

    String quote(String identifier);

    String quoteQualified(String qualifiedIdentifier);

}
