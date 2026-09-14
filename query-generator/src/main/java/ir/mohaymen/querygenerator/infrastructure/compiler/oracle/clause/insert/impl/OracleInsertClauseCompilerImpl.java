package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.impl;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.domain.insert.Insert;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.OracleInsertClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.source.compiler.OracleInsertSourceCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.source.compiler.OracleInsertSourceCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinderImpl;

import java.util.Objects;

public class OracleInsertClauseCompilerImpl implements OracleInsertClauseCompiler {

    private static final String CLAUSE_SEPARATOR = " ";

    private final OracleInsertSourceCompiler insertSourceCompiler;

    public OracleInsertClauseCompilerImpl() {
        this(new OracleInsertSourceCompilerImpl());
    }

    public OracleInsertClauseCompilerImpl(OracleInsertSourceCompiler insertSourceCompiler) {
        this.insertSourceCompiler = Objects.requireNonNull(insertSourceCompiler, "insert source compiler must not be null");
    }

    @Override
    public QueryContext generateInsertClause(QueryContext context) {
        Objects.requireNonNull(context, "query context must not be null");

        Insert insert = context.insert();
        if (insert == null) {
            throw new IllegalArgumentException("insert must not be null");
        }

        OracleParameterBinder parameterBinder = new OracleParameterBinderImpl(context.parameters(), context.parameterMode());
        String clause = insertSourceCompiler.compile(insert, parameterBinder);

        StringBuilder query = context.query();
        if (!query.isEmpty()) {
            query.append(CLAUSE_SEPARATOR);
        }
        query.append(clause);
        return context;
    }

}
