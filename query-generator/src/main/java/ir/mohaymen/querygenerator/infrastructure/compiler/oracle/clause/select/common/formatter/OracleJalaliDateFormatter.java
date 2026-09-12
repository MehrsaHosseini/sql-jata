package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.common.formatter;

import ir.mohaymen.querygenerator.domain.schema.enumeration.DisplayFormatting;

public class OracleJalaliDateFormatter implements OracleDisplayFormatter {

    private static final String TEMPLATE = "TO_CHAR(%s, 'YYYY/MM/DD', 'NLS_CALENDAR=Persian')";

    @Override
    public DisplayFormatting displayFormatting() {
        return DisplayFormatting.TO_JALALI;
    }

    @Override
    public String format(String expression) {
        return TEMPLATE.formatted(expression);
    }

}
