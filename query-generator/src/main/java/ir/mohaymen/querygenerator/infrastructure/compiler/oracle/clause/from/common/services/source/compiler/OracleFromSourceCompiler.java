package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.from.common.services.source.compiler;

import ir.mohaymen.querygenerator.domain.from.From;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

public interface OracleFromSourceCompiler {

    String compile(From from, OracleParameterBinder parameterBinder);

}
