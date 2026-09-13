package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.common.services.alias.compiler;

import ir.mohaymen.querygenerator.domain.having.HavingCondition;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

public interface OracleHavingAliasConditionCompiler {

    String compile(HavingCondition havingCondition, OracleParameterBinder parameterBinder);

}
