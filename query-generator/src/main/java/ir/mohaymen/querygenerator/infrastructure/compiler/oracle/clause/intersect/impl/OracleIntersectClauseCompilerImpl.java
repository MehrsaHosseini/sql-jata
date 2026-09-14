package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.intersect.impl;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.domain.intersect.Intersect;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.intersect.OracleIntersectClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.intersect.common.services.source.compiler.OracleIntersectCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.intersect.common.services.source.compiler.OracleIntersectCompilerImpl;

import java.util.Objects;

public class OracleIntersectClauseCompilerImpl implements OracleIntersectClauseCompiler {

    private static final String CLAUSE_SEPARATOR = " ";

    private final OracleIntersectCompiler intersectCompiler;

    public OracleIntersectClauseCompilerImpl() {
        this(new OracleIntersectCompilerImpl());
    }

    public OracleIntersectClauseCompilerImpl(OracleIntersectCompiler intersectCompiler) {
        this.intersectCompiler = Objects.requireNonNull(intersectCompiler, "intersect compiler must not be null");
    }

    @Override
    public QueryContext generateIntersectClause(QueryContext context) {
        Objects.requireNonNull(context, "query context must not be null");

        Intersect intersect = context.intersect();
        if (intersect == null) {
            return context;
        }

        String clause = intersectCompiler.compile(intersect);

        StringBuilder query = context.query();
        if (!query.isEmpty()) {
            query.append(CLAUSE_SEPARATOR);
        }
        query.append(clause);
        return context;
    }

}
