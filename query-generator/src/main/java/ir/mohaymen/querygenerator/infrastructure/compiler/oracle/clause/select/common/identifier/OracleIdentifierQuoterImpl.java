package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.common.identifier;

import java.util.StringJoiner;

public class OracleIdentifierQuoterImpl implements OracleIdentifierQuoter {

    private static final String QUOTE = "\"";
    private static final String WILDCARD = "*";
    private static final String QUALIFIER_SEPARATOR = ".";
    private static final String QUALIFIER_PATTERN = "\\.";

    @Override
    public String quoteQualified(String qualifiedIdentifier) {
        if (qualifiedIdentifier == null || qualifiedIdentifier.isBlank()) {
            throw new IllegalArgumentException("identifier must not be null or blank");
        }

        StringJoiner identifier = new StringJoiner(QUALIFIER_SEPARATOR);
        for (String part : qualifiedIdentifier.trim().split(QUALIFIER_PATTERN, -1)) {
            identifier.add(quote(part));
        }
        return identifier.toString();
    }

    @Override
    public String quote(String identifier) {
        if (identifier == null) {
            throw new IllegalArgumentException("identifier must not be null");
        }

        String bareIdentifier = unquote(identifier.trim());
        if (bareIdentifier.isBlank()) {
            throw new IllegalArgumentException("identifier part must not be blank: " + identifier);
        }
        if (WILDCARD.equals(bareIdentifier)) {
            return WILDCARD;
        }
        if (bareIdentifier.contains(QUOTE)) {
            throw new IllegalArgumentException("oracle identifier must not contain a double quote: " + identifier);
        }
        return QUOTE + bareIdentifier + QUOTE;
    }

    private static String unquote(String identifier) {
        if (identifier.length() > 1 && identifier.startsWith(QUOTE) && identifier.endsWith(QUOTE)) {
            return identifier.substring(1, identifier.length() - 1);
        }
        return identifier;
    }

}
