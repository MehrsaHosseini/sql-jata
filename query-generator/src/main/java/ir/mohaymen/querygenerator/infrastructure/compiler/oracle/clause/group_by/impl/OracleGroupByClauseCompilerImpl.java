package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.group_by.impl;

import ir.mohaymen.querygenerator.application.query.generate.common.model.QueryContext;
import ir.mohaymen.querygenerator.domain.group.GroupBy;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.group_by.OracleGroupByClauseCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.group_by.common.services.grouping.compiler.OracleGroupingCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.group_by.common.services.grouping.compiler.OracleGroupingCompilerImpl;

import java.util.Objects;

public class OracleGroupByClauseCompilerImpl implements OracleGroupByClauseCompiler {

    private static final String GROUP_BY_KEYWORD = "GROUP BY ";
    private static final String CLAUSE_SEPARATOR = " ";

    private final OracleGroupingCompiler groupingCompiler;

    public OracleGroupByClauseCompilerImpl() {
        this(new OracleGroupingCompilerImpl());
    }

    public OracleGroupByClauseCompilerImpl(OracleGroupingCompiler groupingCompiler) {
        this.groupingCompiler = Objects.requireNonNull(groupingCompiler, "grouping compiler must not be null");
    }

    @Override
    public QueryContext generateGroupByClause(QueryContext context) {
        Objects.requireNonNull(context, "query context must not be null");

        GroupBy groupBy = context.groupBy();
        if (groupBy == null) {
            return context;
        }

        String grouping = groupingCompiler.compile(groupBy);

        StringBuilder query = context.query();
        if (!query.isEmpty()) {
            query.append(CLAUSE_SEPARATOR);
        }
        query.append(GROUP_BY_KEYWORD).append(grouping);
        return context;
    }

}
