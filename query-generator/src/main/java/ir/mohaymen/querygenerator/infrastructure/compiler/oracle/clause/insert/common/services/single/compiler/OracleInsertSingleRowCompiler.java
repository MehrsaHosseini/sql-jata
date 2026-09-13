package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.single.compiler;

import ir.mohaymen.querygenerator.domain.insert.InsertSingleRow;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

public interface OracleInsertSingleRowCompiler {

    String compile(InsertSingleRow insert, OracleParameterBinder parameterBinder);

}
