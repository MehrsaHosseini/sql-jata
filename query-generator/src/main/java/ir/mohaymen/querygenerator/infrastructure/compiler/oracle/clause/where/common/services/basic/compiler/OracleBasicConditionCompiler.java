package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.basic.compiler;

import ir.mohaymen.querygenerator.domain.where.BasicWhere;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

public interface OracleBasicConditionCompiler {

    String compile(BasicWhere basicWhere, OracleParameterBinder parameterBinder);

}
