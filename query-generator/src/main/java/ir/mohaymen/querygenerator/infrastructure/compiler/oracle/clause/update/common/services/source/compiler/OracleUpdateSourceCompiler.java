package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.update.common.services.source.compiler;

import ir.mohaymen.querygenerator.domain.update.Update;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

public interface OracleUpdateSourceCompiler {

    String compile(Update update, OracleParameterBinder parameterBinder);

}
