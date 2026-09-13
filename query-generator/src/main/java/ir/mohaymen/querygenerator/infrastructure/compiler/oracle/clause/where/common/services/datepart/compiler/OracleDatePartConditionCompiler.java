package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.datepart.compiler;

import ir.mohaymen.querygenerator.domain.where.WhereDatePart;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

public interface OracleDatePartConditionCompiler {

    String compile(WhereDatePart whereDatePart, OracleParameterBinder parameterBinder);

}
