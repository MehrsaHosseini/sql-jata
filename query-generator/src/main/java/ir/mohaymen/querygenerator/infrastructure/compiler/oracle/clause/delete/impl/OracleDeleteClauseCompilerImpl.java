package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.delete.impl;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.domain.delete.Delete;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.delete.OracleDeleteClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.delete.common.services.source.compiler.OracleDeleteSourceCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.delete.common.services.source.compiler.OracleDeleteSourceCompilerImpl;

import java.util.Objects;

public class OracleDeleteClauseCompilerImpl implements OracleDeleteClauseCompiler {

    private static final String CLAUSE_SEPARATOR = " ";

    private final OracleDeleteSourceCompiler deleteSourceCompiler;

    public OracleDeleteClauseCompilerImpl() {
        this(new OracleDeleteSourceCompilerImpl());
    }

    public OracleDeleteClauseCompilerImpl(OracleDeleteSourceCompiler deleteSourceCompiler) {
        this.deleteSourceCompiler = Objects.requireNonNull(deleteSourceCompiler, "delete source compiler must not be null");
    }

    @Override
    public QueryContext generateDeleteClause(QueryContext context) {
        Objects.requireNonNull(context, "query context must not be null");

        Delete delete = context.delete();
        if (delete == null) {
            throw new IllegalArgumentException("delete must not be null");
        }

        String clause = deleteSourceCompiler.compile(delete);

        StringBuilder query = context.query();
        if (!query.isEmpty()) {
            query.append(CLAUSE_SEPARATOR);
        }
        query.append(clause);
        return context;
    }

}
