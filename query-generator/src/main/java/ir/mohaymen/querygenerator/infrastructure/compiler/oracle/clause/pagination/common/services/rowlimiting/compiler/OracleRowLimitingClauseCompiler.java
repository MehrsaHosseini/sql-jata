package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.services.rowlimiting.compiler;

import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.model.OraclePaginationWindow;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

public interface OracleRowLimitingClauseCompiler {

    String compile(OraclePaginationWindow window, OracleParameterBinder parameterBinder);

}
