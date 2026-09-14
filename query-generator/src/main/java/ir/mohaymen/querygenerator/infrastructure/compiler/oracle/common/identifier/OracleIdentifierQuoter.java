package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.identifier;

public interface OracleIdentifierQuoter {

    String quote(String identifier);

    String quoteQualified(String qualifiedIdentifier);

}
