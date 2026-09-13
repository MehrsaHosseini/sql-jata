package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.update.common.services.item.compiler;

import ir.mohaymen.querygenerator.domain.update.UpdateSet;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

public interface OracleUpdateSetItemCompiler {

    String compile(UpdateSet updateSet, OracleParameterBinder parameterBinder);

}
