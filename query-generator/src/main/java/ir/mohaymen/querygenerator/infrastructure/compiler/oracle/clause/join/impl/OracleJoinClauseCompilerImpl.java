package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.impl;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.OracleJoinClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.services.source.compiler.OracleJoinSourceCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.services.source.compiler.OracleJoinSourceCompilerImpl;

import java.util.Objects;

public class OracleJoinClauseCompilerImpl implements OracleJoinClauseCompiler {

    private static final String CLAUSE_SEPARATOR = " ";

    private final OracleJoinSourceCompiler joinSourceCompiler;

    public OracleJoinClauseCompilerImpl() {
        this(new OracleJoinSourceCompilerImpl());
    }

    public OracleJoinClauseCompilerImpl(OracleJoinSourceCompiler joinSourceCompiler) {
        this.joinSourceCompiler = Objects.requireNonNull(joinSourceCompiler, "join source compiler must not be null");
    }

    @Override
    public QueryContext generateJoinClause(QueryContext context) {
        Objects.requireNonNull(context, "query context must not be null");

        String clause = joinSourceCompiler.compile(context.join());
        if (clause.isEmpty()) {
            return context;
        }

        StringBuilder query = context.query();
        if (!query.isEmpty()) {
            query.append(CLAUSE_SEPARATOR);
        }
        query.append(clause);
        return context;
    }

}
