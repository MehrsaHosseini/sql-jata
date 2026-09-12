package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.common.formatter;

import ir.mohaymen.querygenerator.domain.schema.enumeration.DisplayFormatting;

public class OracleSeparatedDigitsFormatter implements OracleDisplayFormatter {

    private static final String TEMPLATE = "TO_CHAR(%s, '%s', 'NLS_NUMERIC_CHARACTERS=''.,''')";
    private static final int NUMBER_MAX_INTEGRAL_DIGITS = 38;
    private static final int DIGIT_GROUP_SIZE = 3;
    private static final String MASK = mask();

    @Override
    public DisplayFormatting displayFormatting() {
        return DisplayFormatting.SEPARATED_DIGITS;
    }

    @Override
    public String format(String expression) {
        return TEMPLATE.formatted(expression, MASK);
    }

    private static String mask() {
        int groupCount = (NUMBER_MAX_INTEGRAL_DIGITS + DIGIT_GROUP_SIZE - 1) / DIGIT_GROUP_SIZE;
        return "FM" + "999G".repeat(groupCount - 1) + "990";
    }

}
