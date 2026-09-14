package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.update.impl;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.domain.update.Update;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.update.OracleUpdateClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.update.common.services.source.compiler.OracleUpdateSourceCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.update.common.services.source.compiler.OracleUpdateSourceCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinderImpl;

import java.util.Objects;

public class OracleUpdateClauseCompilerImpl implements OracleUpdateClauseCompiler {

    private static final String CLAUSE_SEPARATOR = " ";

    private final OracleUpdateSourceCompiler updateSourceCompiler;

    public OracleUpdateClauseCompilerImpl() {
        this(new OracleUpdateSourceCompilerImpl());
    }

    public OracleUpdateClauseCompilerImpl(OracleUpdateSourceCompiler updateSourceCompiler) {
        this.updateSourceCompiler = Objects.requireNonNull(updateSourceCompiler, "update source compiler must not be null");
    }

    @Override
    public QueryContext generateUpdateClause(QueryContext context) {
        Objects.requireNonNull(context, "query context must not be null");

        Update update = context.update();
        if (update == null) {
            throw new IllegalArgumentException("update must not be null");
        }

        OracleParameterBinder parameterBinder = new OracleParameterBinderImpl(context.parameters(), context.parameterMode());
        String clause = updateSourceCompiler.compile(update, parameterBinder);

        StringBuilder query = context.query();
        if (!query.isEmpty()) {
            query.append(CLAUSE_SEPARATOR);
        }
        query.append(clause);
        return context;
    }

}
