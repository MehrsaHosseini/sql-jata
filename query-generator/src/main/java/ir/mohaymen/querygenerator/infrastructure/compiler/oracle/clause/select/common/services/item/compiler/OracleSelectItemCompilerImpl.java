package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.common.services.item.compiler;

import ir.mohaymen.querygenerator.domain.schema.column.Column;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.common.formatter.provider.OracleDisplayFormatterProvider;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.common.formatter.provider.impl.OracleDisplayFormatterProviderImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.identifier.OracleIdentifierQuoter;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.identifier.OracleIdentifierQuoterImpl;

import java.util.Objects;

public class OracleSelectItemCompilerImpl implements OracleSelectItemCompiler {

    private final String ALIAS_KEYWORD = " AS ";

    private final OracleIdentifierQuoter identifierQuoter;
    private final OracleDisplayFormatterProvider displayFormattingCompiler;

    public OracleSelectItemCompilerImpl() {
        this(new OracleIdentifierQuoterImpl(), new OracleDisplayFormatterProviderImpl());
    }

    public OracleSelectItemCompilerImpl(OracleIdentifierQuoter identifierQuoter,
                                        OracleDisplayFormatterProvider displayFormattingCompiler) {
        this.identifierQuoter = Objects.requireNonNull(identifierQuoter, "identifier quoter must not be null");
        this.displayFormattingCompiler = Objects.requireNonNull(displayFormattingCompiler, "display formatting compiler must not be null");
    }

    @Override
    public String compile(Column column) {
        if (column == null) {
            throw new IllegalArgumentException("select column must not be null");
        }

        String expression = displayFormattingCompiler.format(
                identifierQuoter.quoteQualified(column.columnName()), column.displayFormatting());

        String alias = column.columnAlias();
        if (alias == null || alias.isBlank()) {
            return expression;
        }
        return expression + ALIAS_KEYWORD + identifierQuoter.quote(alias);
    }

}
