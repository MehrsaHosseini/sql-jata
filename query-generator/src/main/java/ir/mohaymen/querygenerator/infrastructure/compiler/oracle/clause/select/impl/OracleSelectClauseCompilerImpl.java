package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.impl;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.OracleSelectClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.common.services.projection.compiler.OracleProjectionCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.common.services.projection.compiler.OracleProjectionCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinderImpl;

import java.util.Objects;

public class OracleSelectClauseCompilerImpl implements OracleSelectClauseCompiler {

    private static final String SELECT_KEYWORD = "SELECT ";

    private final OracleProjectionCompiler projectionCompiler;

    public OracleSelectClauseCompilerImpl() {
        this(new OracleProjectionCompilerImpl());
    }

    public OracleSelectClauseCompilerImpl(OracleProjectionCompiler projectionCompiler) {
        this.projectionCompiler = Objects.requireNonNull(projectionCompiler, "projection compiler must not be null");
    }

    @Override
    public QueryContext generateSelectClause(QueryContext context) {
        Objects.requireNonNull(context, "query context must not be null");

        OracleParameterBinder parameterBinder = new OracleParameterBinderImpl(context.parameters(), context.parameterMode());
        context.query().append(SELECT_KEYWORD).append(projectionCompiler.compile(context.select(), parameterBinder));
        return context;
    }

}
