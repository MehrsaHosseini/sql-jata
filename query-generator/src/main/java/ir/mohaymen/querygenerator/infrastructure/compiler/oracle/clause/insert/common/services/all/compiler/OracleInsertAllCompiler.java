package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.all.compiler;

import ir.mohaymen.querygenerator.domain.insert.InsertAll;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

public interface OracleInsertAllCompiler {

    String compile(InsertAll insert, OracleParameterBinder parameterBinder);

}
