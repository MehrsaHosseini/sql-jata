package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.multiple.compiler;

import ir.mohaymen.querygenerator.domain.where.MultipleWhere;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.condition.compiler.OracleConditionCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

public interface OracleMultipleConditionCompiler {

    String compile(MultipleWhere multipleWhere, OracleConditionCompiler conditionCompiler, OracleParameterBinder parameterBinder);

}
