package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.impl;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.domain.having.Having;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.OracleHavingClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.common.model.OracleHavingCondition;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.common.services.condition.compiler.OracleHavingConditionCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.common.services.condition.compiler.OracleHavingConditionCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.common.services.wrapper.compiler.OracleHavingWrapperCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.common.services.wrapper.compiler.OracleHavingWrapperCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinderImpl;

import java.util.Objects;

public class OracleHavingClauseCompilerImpl implements OracleHavingClauseCompiler {

    private final String HAVING_KEYWORD = "HAVING ";
    private final String CLAUSE_SEPARATOR = " ";

    private final OracleHavingConditionCompiler havingConditionCompiler;
    private final OracleHavingWrapperCompiler havingWrapperCompiler;

    public OracleHavingClauseCompilerImpl() {
        this(new OracleHavingConditionCompilerImpl(), new OracleHavingWrapperCompilerImpl());
    }

    public OracleHavingClauseCompilerImpl(OracleHavingConditionCompiler havingConditionCompiler,
                                          OracleHavingWrapperCompiler havingWrapperCompiler) {
        this.havingConditionCompiler = Objects.requireNonNull(havingConditionCompiler, "having condition compiler must not be null");
        this.havingWrapperCompiler = Objects.requireNonNull(havingWrapperCompiler, "having wrapper compiler must not be null");
    }

    @Override
    public QueryContext generateHavingClause(QueryContext context) {
        Objects.requireNonNull(context, "query context must not be null");

        Having having = context.having();
        if (having == null) {
            return context;
        }

        OracleParameterBinder parameterBinder = new OracleParameterBinderImpl(context.parameters());
        OracleHavingCondition condition = havingConditionCompiler.compile(having, parameterBinder);

        StringBuilder query = context.query();
        if (condition.requiresWrapper()) {
            String wrapped = havingWrapperCompiler.wrap(query.toString(), condition.condition());
            query.setLength(0);
            query.append(wrapped);
            return context;
        }

        if (!query.isEmpty()) {
            query.append(CLAUSE_SEPARATOR);
        }
        query.append(HAVING_KEYWORD).append(condition.condition());
        return context;
    }

}
