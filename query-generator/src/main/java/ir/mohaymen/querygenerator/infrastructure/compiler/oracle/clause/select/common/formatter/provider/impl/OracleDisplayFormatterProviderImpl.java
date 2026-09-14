package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.common.formatter.provider.impl;

import ir.mohaymen.querygenerator.domain.schema.enumeration.DisplayFormatting;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.common.formatter.OracleDisplayFormatter;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.common.formatter.provider.OracleDisplayFormatterProvider;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.common.formatter.OracleJalaliDateFormatter;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.common.formatter.OracleSeparatedDigitsFormatter;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OracleDisplayFormatterProviderImpl implements OracleDisplayFormatterProvider {

    private final Map<DisplayFormatting, OracleDisplayFormatter> formatters;

    public OracleDisplayFormatterProviderImpl() {
        this(List.of(new OracleJalaliDateFormatter(), new OracleSeparatedDigitsFormatter()));
    }

    public OracleDisplayFormatterProviderImpl(List<OracleDisplayFormatter> formatters) {
        Objects.requireNonNull(formatters, "formatters must not be null");
        this.formatters = index(formatters);
        requireEveryFormattingCovered();
    }

    @Override
    public String format(String expression, DisplayFormatting displayFormatting) {
        if (displayFormatting == null) {
            return expression;
        }
        return formatters.get(displayFormatting).format(expression);
    }

    private static Map<DisplayFormatting, OracleDisplayFormatter> index(List<OracleDisplayFormatter> formatters) {
        Map<DisplayFormatting, OracleDisplayFormatter> indexed = new EnumMap<>(DisplayFormatting.class);
        for (OracleDisplayFormatter formatter : formatters) {
            OracleDisplayFormatter duplicate = indexed.put(formatter.displayFormatting(), formatter);
            if (duplicate != null) {
                throw new IllegalArgumentException("duplicate oracle formatter for: " + formatter.displayFormatting());
            }
        }
        return indexed;
    }

    private void requireEveryFormattingCovered() {
        EnumSet<DisplayFormatting> missing = EnumSet.allOf(DisplayFormatting.class);
        missing.removeAll(formatters.keySet());
        if (!missing.isEmpty()) {
            throw new IllegalArgumentException("no oracle formatter registered for: " + missing);
        }
    }

}
