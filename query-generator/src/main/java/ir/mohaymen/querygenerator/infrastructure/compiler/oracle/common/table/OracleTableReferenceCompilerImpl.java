package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.table;

import ir.mohaymen.querygenerator.domain.schema.table.Table;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.identifier.OracleIdentifierQuoter;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.identifier.OracleIdentifierQuoterImpl;

import java.util.Objects;

public class OracleTableReferenceCompilerImpl implements OracleTableReferenceCompiler {

    private final String ALIAS_SEPARATOR = " ";
    private final String WILDCARD = "*";

    private final OracleIdentifierQuoter identifierQuoter;

    public OracleTableReferenceCompilerImpl() {
        this(new OracleIdentifierQuoterImpl());
    }

    public OracleTableReferenceCompilerImpl(OracleIdentifierQuoter identifierQuoter) {
        this.identifierQuoter = Objects.requireNonNull(identifierQuoter, "identifier quoter must not be null");
    }

    @Override
    public String compile(Table table) {
        if (table == null) {
            throw new IllegalArgumentException("table must not be null");
        }

        String reference = requireNoWildcard(identifierQuoter.quoteQualified(table.tableName()), table.tableName());

        String alias = table.tableAlias();
        if (alias == null || alias.isBlank()) {
            return reference;
        }
        return reference + ALIAS_SEPARATOR + requireNoWildcard(identifierQuoter.quote(alias), alias);
    }

    private String requireNoWildcard(String quotedIdentifier, String identifier) {
        if (quotedIdentifier.contains(WILDCARD)) {
            throw new IllegalArgumentException("table identifier must not contain a wildcard: " + identifier);
        }
        return quotedIdentifier;
    }

}
