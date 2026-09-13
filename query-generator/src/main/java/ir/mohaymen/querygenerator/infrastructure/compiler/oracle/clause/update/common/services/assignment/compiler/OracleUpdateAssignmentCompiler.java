package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.update.common.services.assignment.compiler;

import ir.mohaymen.querygenerator.domain.update.UpdateSet;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

import java.util.List;

public interface OracleUpdateAssignmentCompiler {

    String compile(List<UpdateSet> setList, OracleParameterBinder parameterBinder);

}
