package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.exists.compiler;

import ir.mohaymen.querygenerator.domain.where.WhereExists;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.condition.compiler.OracleConditionCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

public interface OracleExistsConditionCompiler {

    String compile(WhereExists whereExists, OracleConditionCompiler conditionCompiler, OracleParameterBinder parameterBinder);

}
