package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.impl;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.domain.where.Where;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.OracleWhereClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.condition.compiler.OracleConditionCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.condition.compiler.OracleConditionCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinderImpl;

import java.util.Objects;

public class OracleWhereClauseCompilerImpl implements OracleWhereClauseCompiler {

    private static final String WHERE_KEYWORD = "WHERE ";
    private static final String CLAUSE_SEPARATOR = " ";

    private final OracleConditionCompiler conditionCompiler;

    public OracleWhereClauseCompilerImpl() {
        this(new OracleConditionCompilerImpl());
    }

    public OracleWhereClauseCompilerImpl(OracleConditionCompiler conditionCompiler) {
        this.conditionCompiler = Objects.requireNonNull(conditionCompiler, "condition compiler must not be null");
    }

    @Override
    public QueryContext generateWhereClause(QueryContext context) {
        Objects.requireNonNull(context, "query context must not be null");

        Where where = context.where();
        if (where == null) {
            return context;
        }

        OracleParameterBinder parameterBinder = new OracleParameterBinderImpl(context.parameters());
        String condition = conditionCompiler.compile(where, parameterBinder);

        StringBuilder query = context.query();
        if (!query.isEmpty()) {
            query.append(CLAUSE_SEPARATOR);
        }
        query.append(WHERE_KEYWORD).append(condition);
        return context;
    }

}
