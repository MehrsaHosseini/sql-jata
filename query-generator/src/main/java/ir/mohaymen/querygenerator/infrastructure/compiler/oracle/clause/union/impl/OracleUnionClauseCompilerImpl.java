package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.union.impl;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.domain.union.Union;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.union.OracleUnionClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.union.common.services.source.compiler.OracleUnionCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.union.common.services.source.compiler.OracleUnionCompilerImpl;

import java.util.Objects;

public class OracleUnionClauseCompilerImpl implements OracleUnionClauseCompiler {

    private static final String CLAUSE_SEPARATOR = " ";

    private final OracleUnionCompiler unionCompiler;

    public OracleUnionClauseCompilerImpl() {
        this(new OracleUnionCompilerImpl());
    }

    public OracleUnionClauseCompilerImpl(OracleUnionCompiler unionCompiler) {
        this.unionCompiler = Objects.requireNonNull(unionCompiler, "union compiler must not be null");
    }

    @Override
    public QueryContext generateUnionClause(QueryContext context) {
        Objects.requireNonNull(context, "query context must not be null");

        Union union = context.union();
        if (union == null) {
            return context;
        }

        String clause = unionCompiler.compile(union);

        StringBuilder query = context.query();
        if (!query.isEmpty()) {
            query.append(CLAUSE_SEPARATOR);
        }
        query.append(clause);
        return context;
    }

}
