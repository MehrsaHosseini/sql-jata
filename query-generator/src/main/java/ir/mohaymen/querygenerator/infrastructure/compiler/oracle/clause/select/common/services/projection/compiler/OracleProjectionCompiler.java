package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.common.services.projection.compiler;

import ir.mohaymen.querygenerator.domain.select.Select;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

public interface OracleProjectionCompiler {

    String compile(Select select, OracleParameterBinder parameterBinder);

}
