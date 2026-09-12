package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.domain.schema.column.Column;
import ir.mohaymen.querygenerator.domain.schema.enumeration.DisplayFormatting;
import ir.mohaymen.querygenerator.domain.select.Select;
import ir.mohaymen.querygenerator.domain.select.SelectColumn;
import ir.mohaymen.querygenerator.domain.select.SelectRaw;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class OracleSelectClauseCompilerImpl implements OracleSelectClauseCompiler {

    private final String SELECT_KEYWORD = "SELECT ";
    private final String ALL_COLUMNS = "*";
    private final String COLUMN_SEPARATOR = ", ";
    private final String ALIAS_KEYWORD = " AS ";
    private final String QUOTE = "\"";
    private final String IDENTIFIER_SEPARATOR = ".";

    private final String JALALI_TEMPLATE = "TO_CHAR(%s, 'YYYY/MM/DD', 'NLS_CALENDAR=Persian')";
    private final String SEPARATED_DIGITS_TEMPLATE = "TO_CHAR(%s, '%s', 'NLS_NUMERIC_CHARACTERS=''.,''')";

    private final int NUMBER_MAX_INTEGRAL_DIGITS = 38;
    private final int DIGIT_GROUP_SIZE = 3;
    private final String SEPARATED_DIGITS_MASK = separatedDigitsMask();

    @Override
    public QueryContext generateSelectClause(QueryContext context) {
        Objects.requireNonNull(context, "query context must not be null");

        context.query().append(SELECT_KEYWORD).append(compileSelect(context.select()));
        return context;
    }

    private String compileSelect(Select select) {
        return switch (select) {
            case null -> ALL_COLUMNS;
            case SelectRaw selectRaw -> compileRaw(selectRaw);
            case SelectColumn selectColumn -> compileColumns(selectColumn.columnList());
        };
    }

    private String compileRaw(SelectRaw selectRaw) {
        String raw = selectRaw.raw();
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("raw select expression must not be null or blank");
        }
        return raw.trim();
    }

    private String compileColumns(List<Column> columnList) {
        if (columnList == null || columnList.isEmpty()) {
            return ALL_COLUMNS;
        }

        StringJoiner columns = new StringJoiner(COLUMN_SEPARATOR);
        for (Column column : columnList) {
            columns.add(compileColumn(column));
        }
        return columns.toString();
    }

    private String compileColumn(Column column) {
        if (column == null) {
            throw new IllegalArgumentException("select column must not be null");
        }

        String expression = format(qualifiedIdentifier(column.columnName()), column.displayFormatting());
        String alias = column.columnAlias();
        if (alias == null || alias.isBlank()) {
            return expression;
        }
        return expression + ALIAS_KEYWORD + quoteIdentifier(alias.trim());
    }

    private String format(String expression, DisplayFormatting displayFormatting) {
        if (displayFormatting == null) {
            return expression;
        }

        return switch (displayFormatting) {
            case TO_JALALI -> JALALI_TEMPLATE.formatted(expression);
            case SEPARATED_DIGITS -> SEPARATED_DIGITS_TEMPLATE.formatted(expression, SEPARATED_DIGITS_MASK);
        };
    }

    private String qualifiedIdentifier(String columnName) {
        if (columnName == null || columnName.isBlank()) {
            throw new IllegalArgumentException("column name must not be null or blank");
        }

        StringJoiner identifier = new StringJoiner(IDENTIFIER_SEPARATOR);
        for (String part : columnName.trim().split("\\.", -1)) {
            identifier.add(quoteIdentifier(part.trim()));
        }
        return identifier.toString();
    }

    private String quoteIdentifier(String identifier) {
        String bareIdentifier = unquote(identifier);
        if (bareIdentifier.isBlank()) {
            throw new IllegalArgumentException("identifier part must not be blank: " + identifier);
        }
        if (ALL_COLUMNS.equals(bareIdentifier)) {
            return ALL_COLUMNS;
        }
        if (bareIdentifier.contains(QUOTE)) {
            throw new IllegalArgumentException("oracle identifier must not contain a double quote: " + identifier);
        }
        return QUOTE + bareIdentifier + QUOTE;
    }

    private String unquote(String identifier) {
        if (identifier.length() > 1 && identifier.startsWith(QUOTE) && identifier.endsWith(QUOTE)) {
            return identifier.substring(1, identifier.length() - 1);
        }
        return identifier;
    }

    private String separatedDigitsMask() {
        int groupCount = (NUMBER_MAX_INTEGRAL_DIGITS + DIGIT_GROUP_SIZE - 1) / DIGIT_GROUP_SIZE;
        return "FM" + "999G".repeat(groupCount - 1) + "990";
    }

}
