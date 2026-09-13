package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.predicate;

import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

public interface OraclePredicateCompiler {

    String compile(String expression, String operation, Object value, OracleParameterBinder parameterBinder);

}
