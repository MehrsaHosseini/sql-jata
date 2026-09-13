package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.column;

import ir.mohaymen.querygenerator.domain.schema.column.Column;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.identifier.OracleIdentifierQuoter;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.identifier.OracleIdentifierQuoterImpl;

import java.util.Objects;

public class OracleColumnReferenceCompilerImpl implements OracleColumnReferenceCompiler {

    private  final String WILDCARD = "*";

    private final OracleIdentifierQuoter identifierQuoter;

    public OracleColumnReferenceCompilerImpl() {
        this(new OracleIdentifierQuoterImpl());
    }

    public OracleColumnReferenceCompilerImpl(OracleIdentifierQuoter identifierQuoter) {
        this.identifierQuoter = Objects.requireNonNull(identifierQuoter, "identifier quoter must not be null");
    }

    @Override
    public String compile(Column column) {
        if (column == null) {
            throw new IllegalArgumentException("column must not be null");
        }

        String reference = identifierQuoter.quoteQualified(column.columnName());
        if (reference.contains(WILDCARD)) {
            throw new IllegalArgumentException("column reference must not contain a wildcard: " + column.columnName());
        }
        return reference;
    }

}
