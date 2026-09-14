package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.common.services.condition.compiler;

import ir.mohaymen.querygenerator.domain.having.Having;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.common.model.OracleHavingCondition;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

public interface OracleHavingConditionCompiler {

    OracleHavingCondition compile(Having having, OracleParameterBinder parameterBinder);

}
