package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.values.compiler;

import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

import java.util.List;

public interface OracleInsertValuesCompiler {

    String compile(List<Object> values, OracleParameterBinder parameterBinder);

}
