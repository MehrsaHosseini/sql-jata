package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.value.compiler;

import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

public interface OracleValueCompiler {

    String compile(Object value, OracleParameterBinder parameterBinder);

    String compileRange(Object value, OracleParameterBinder parameterBinder);

}
