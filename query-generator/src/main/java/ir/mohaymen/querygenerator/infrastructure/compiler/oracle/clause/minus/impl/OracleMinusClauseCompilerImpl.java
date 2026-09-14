package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.minus.impl;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.domain.minus.Minus;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.minus.OracleMinusClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.minus.common.services.source.compiler.OracleMinusCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.minus.common.services.source.compiler.OracleMinusCompilerImpl;

import java.util.Objects;

public class OracleMinusClauseCompilerImpl implements OracleMinusClauseCompiler {

    private static final String CLAUSE_SEPARATOR = " ";

    private final OracleMinusCompiler minusCompiler;

    public OracleMinusClauseCompilerImpl() {
        this(new OracleMinusCompilerImpl());
    }

    public OracleMinusClauseCompilerImpl(OracleMinusCompiler minusCompiler) {
        this.minusCompiler = Objects.requireNonNull(minusCompiler, "minus compiler must not be null");
    }

    @Override
    public QueryContext generateMinusClause(QueryContext context) {
        Objects.requireNonNull(context, "query context must not be null");

        Minus minus = context.minus();
        if (minus == null) {
            return context;
        }

        String clause = minusCompiler.compile(minus);

        StringBuilder query = context.query();
        if (!query.isEmpty()) {
            query.append(CLAUSE_SEPARATOR);
        }
        query.append(clause);
        return context;
    }

}
