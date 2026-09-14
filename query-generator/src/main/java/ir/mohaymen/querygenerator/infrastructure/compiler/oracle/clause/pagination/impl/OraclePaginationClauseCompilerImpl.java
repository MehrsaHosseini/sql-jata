package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.impl;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.OraclePaginationClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.model.OraclePaginationWindow;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.services.rowlimiting.compiler.OracleRowLimitingClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.services.rowlimiting.compiler.OracleRowLimitingClauseCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.services.window.resolver.OraclePaginationWindowResolver;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.services.window.resolver.OraclePaginationWindowResolverImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinderImpl;

import java.util.Objects;

public class OraclePaginationClauseCompilerImpl implements OraclePaginationClauseCompiler {

    private static final String CLAUSE_SEPARATOR = " ";

    private final OraclePaginationWindowResolver paginationWindowResolver;
    private final OracleRowLimitingClauseCompiler rowLimitingClauseCompiler;

    public OraclePaginationClauseCompilerImpl() {
        this(new OraclePaginationWindowResolverImpl(), new OracleRowLimitingClauseCompilerImpl());
    }

    public OraclePaginationClauseCompilerImpl(OraclePaginationWindowResolver paginationWindowResolver,
                                              OracleRowLimitingClauseCompiler rowLimitingClauseCompiler) {
        this.paginationWindowResolver = Objects.requireNonNull(paginationWindowResolver, "pagination window resolver must not be null");
        this.rowLimitingClauseCompiler = Objects.requireNonNull(rowLimitingClauseCompiler, "row limiting clause compiler must not be null");
    }

    @Override
    public QueryContext generatePaginationClause(QueryContext context) {
        Objects.requireNonNull(context, "query context must not be null");

        OraclePaginationWindow window = paginationWindowResolver.resolve(context.pagination());
        if (window.isEmpty()) {
            return context;
        }

        OracleParameterBinder parameterBinder = new OracleParameterBinderImpl(context.parameters(), context.parameterMode());
        String clause = rowLimitingClauseCompiler.compile(window, parameterBinder);

        StringBuilder query = context.query();
        if (!query.isEmpty()) {
            query.append(CLAUSE_SEPARATOR);
        }
        query.append(clause);
        return context;
    }

}
