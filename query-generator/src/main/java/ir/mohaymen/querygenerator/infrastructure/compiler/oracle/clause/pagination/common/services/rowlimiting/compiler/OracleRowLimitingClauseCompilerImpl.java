package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.services.rowlimiting.compiler;

import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.model.OraclePaginationWindow;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

import java.util.Objects;
import java.util.StringJoiner;

public class OracleRowLimitingClauseCompilerImpl implements OracleRowLimitingClauseCompiler {

    private static final String OFFSET_PREFIX = "OFFSET ";
    private static final String OFFSET_SUFFIX = " ROWS";
    private static final String FETCH_PREFIX = "FETCH NEXT ";
    private static final String FETCH_SUFFIX = " ROWS ONLY";
    private static final String PART_SEPARATOR = " ";
    private static final String EMPTY_CLAUSE = "";

    @Override
    public String compile(OraclePaginationWindow window, OracleParameterBinder parameterBinder) {
        Objects.requireNonNull(window, "pagination window must not be null");
        Objects.requireNonNull(parameterBinder, "parameter binder must not be null");

        if (window.isEmpty()) {
            return EMPTY_CLAUSE;
        }

        StringJoiner clause = new StringJoiner(PART_SEPARATOR);
        if (window.hasOffset()) {
            clause.add(OFFSET_PREFIX + parameterBinder.bind(window.offset()) + OFFSET_SUFFIX);
        }
        if (window.hasLimit()) {
            clause.add(FETCH_PREFIX + parameterBinder.bind(window.limit()) + FETCH_SUFFIX);
        }
        return clause.toString();
    }

}
