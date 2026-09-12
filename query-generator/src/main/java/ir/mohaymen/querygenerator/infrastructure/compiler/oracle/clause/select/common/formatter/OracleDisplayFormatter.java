package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.common.formatter;

import ir.mohaymen.querygenerator.domain.schema.enumeration.DisplayFormatting;

public interface OracleDisplayFormatter {

    DisplayFormatting displayFormatting();

    String format(String expression);

}
