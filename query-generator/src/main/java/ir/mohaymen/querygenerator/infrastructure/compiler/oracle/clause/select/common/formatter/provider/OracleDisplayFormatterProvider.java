package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.common.formatter.provider;

import ir.mohaymen.querygenerator.domain.schema.enumeration.DisplayFormatting;

public interface OracleDisplayFormatterProvider {

    String format(String expression, DisplayFormatting displayFormatting);

}
