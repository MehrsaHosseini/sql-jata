package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.datepart.expression;

import ir.mohaymen.querygenerator.domain.schema.enumeration.DatePart;

public interface OracleDatePartExpressionCompiler {

    String compile(String columnReference, DatePart datePart);

}
