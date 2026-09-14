package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.common.services.multiple.compiler;

import ir.mohaymen.querygenerator.domain.having.MultipleHaving;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.common.model.OracleHavingCondition;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.common.services.condition.compiler.OracleHavingConditionCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

public interface OracleMultipleHavingCompiler {

    OracleHavingCondition compile(MultipleHaving multipleHaving,
                                  OracleHavingConditionCompiler conditionCompiler,
                                  OracleParameterBinder parameterBinder);

}
