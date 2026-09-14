package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.source.compiler;

import ir.mohaymen.querygenerator.domain.insert.Insert;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

public interface OracleInsertSourceCompiler {

    String compile(Insert insert, OracleParameterBinder parameterBinder);

}
