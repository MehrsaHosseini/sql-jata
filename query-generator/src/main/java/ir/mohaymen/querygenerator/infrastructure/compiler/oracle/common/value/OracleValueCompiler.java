package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.value;

import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

public interface OracleValueCompiler {

    String compile(Object value, OracleParameterBinder parameterBinder);

    String compileRange(Object value, OracleParameterBinder parameterBinder);

    String compileNullable(Object value, OracleParameterBinder parameterBinder);

}
