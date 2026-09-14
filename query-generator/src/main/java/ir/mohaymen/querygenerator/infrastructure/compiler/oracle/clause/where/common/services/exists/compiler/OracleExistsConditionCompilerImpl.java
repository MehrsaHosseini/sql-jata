package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.exists.compiler;

import ir.mohaymen.querygenerator.domain.where.Where;
import ir.mohaymen.querygenerator.domain.where.WhereExists;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.condition.compiler.OracleConditionCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.table.OracleTableReferenceCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.table.OracleTableReferenceCompilerImpl;

import java.util.Objects;

public class OracleExistsConditionCompilerImpl implements OracleExistsConditionCompiler {

    private static final String SUBQUERY_PREFIX = "EXISTS (SELECT 1 FROM ";
    private static final String SUBQUERY_SUFFIX = ")";
    private static final String WHERE_KEYWORD = " WHERE ";

    private final OracleTableReferenceCompiler tableReferenceCompiler;

    public OracleExistsConditionCompilerImpl() {
        this(new OracleTableReferenceCompilerImpl());
    }

    public OracleExistsConditionCompilerImpl(OracleTableReferenceCompiler tableReferenceCompiler) {
        this.tableReferenceCompiler = Objects.requireNonNull(tableReferenceCompiler, "table reference compiler must not be null");
    }

    @Override
    public String compile(WhereExists whereExists,
                          OracleConditionCompiler conditionCompiler,
                          OracleParameterBinder parameterBinder) {
        if (whereExists == null) {
            throw new IllegalArgumentException("exists condition must not be null");
        }
        Objects.requireNonNull(conditionCompiler, "condition compiler must not be null");

        StringBuilder subQuery = new StringBuilder(SUBQUERY_PREFIX)
                .append(tableReferenceCompiler.compile(whereExists.table()));

        Where nestedCondition = whereExists.where();
        if (nestedCondition != null) {
            subQuery.append(WHERE_KEYWORD).append(conditionCompiler.compile(nestedCondition, parameterBinder));
        }
        return subQuery.append(SUBQUERY_SUFFIX).toString();
    }

}
