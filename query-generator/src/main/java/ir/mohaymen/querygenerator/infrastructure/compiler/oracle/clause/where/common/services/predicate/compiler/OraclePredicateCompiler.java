package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.predicate.compiler;

import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

public interface OraclePredicateCompiler {

    String compile(String expression, String operation, Object value, OracleParameterBinder parameterBinder);

}
