package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.from.impl;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.from.OracleFromClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.from.common.services.source.compiler.OracleFromSourceCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.from.common.services.source.compiler.OracleFromSourceCompilerImpl;

import java.util.Objects;

public class OracleFromClauseCompilerImpl implements OracleFromClauseCompiler {

    private final String FROM_KEYWORD = "FROM ";
    private final String CLAUSE_SEPARATOR = " ";

    private final OracleFromSourceCompiler fromSourceCompiler;

    public OracleFromClauseCompilerImpl() {
        this(new OracleFromSourceCompilerImpl());
    }

    public OracleFromClauseCompilerImpl(OracleFromSourceCompiler fromSourceCompiler) {
        this.fromSourceCompiler = Objects.requireNonNull(fromSourceCompiler, "from source compiler must not be null");
    }

    @Override
    public QueryContext generateFromClause(QueryContext context) {
        Objects.requireNonNull(context, "query context must not be null");

        StringBuilder query = context.query();
        if (!query.isEmpty()) {
            query.append(CLAUSE_SEPARATOR);
        }
        query.append(FROM_KEYWORD).append(fromSourceCompiler.compile(context.from()));
        return context;
    }

}
