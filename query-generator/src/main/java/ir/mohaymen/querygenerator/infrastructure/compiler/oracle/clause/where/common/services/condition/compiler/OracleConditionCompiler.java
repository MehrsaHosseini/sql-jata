package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.condition.compiler;

import ir.mohaymen.querygenerator.domain.where.Where;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

public interface OracleConditionCompiler {

    String compile(Where where, OracleParameterBinder parameterBinder);

}
