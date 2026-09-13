package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.common.services.condition.compiler;

import ir.mohaymen.querygenerator.domain.having.Having;
import ir.mohaymen.querygenerator.domain.having.HavingCondition;
import ir.mohaymen.querygenerator.domain.having.HavingRaw;
import ir.mohaymen.querygenerator.domain.having.MultipleHaving;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.common.model.OracleHavingCondition;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.common.services.alias.compiler.OracleHavingAliasConditionCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.common.services.alias.compiler.OracleHavingAliasConditionCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.common.services.multiple.compiler.OracleMultipleHavingCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.common.services.multiple.compiler.OracleMultipleHavingCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

import java.util.Objects;

public class OracleHavingConditionCompilerImpl implements OracleHavingConditionCompiler {

    private static final boolean WRAPPED = true;
    private static final boolean IN_PLACE = false;

    private final OracleHavingAliasConditionCompiler aliasConditionCompiler;
    private final OracleMultipleHavingCompiler multipleHavingCompiler;

    public OracleHavingConditionCompilerImpl() {
        this(new OracleHavingAliasConditionCompilerImpl(), new OracleMultipleHavingCompilerImpl());
    }

    public OracleHavingConditionCompilerImpl(OracleHavingAliasConditionCompiler aliasConditionCompiler,
                                             OracleMultipleHavingCompiler multipleHavingCompiler) {
        this.aliasConditionCompiler = Objects.requireNonNull(aliasConditionCompiler, "alias condition compiler must not be null");
        this.multipleHavingCompiler = Objects.requireNonNull(multipleHavingCompiler, "multiple having compiler must not be null");
    }

    @Override
    public OracleHavingCondition compile(Having having, OracleParameterBinder parameterBinder) {
        Objects.requireNonNull(parameterBinder, "parameter binder must not be null");

        return switch (having) {
            case null -> throw new IllegalArgumentException("having condition must not be null");
            case HavingRaw havingRaw -> new OracleHavingCondition(compileRaw(havingRaw.raw()), IN_PLACE);
            case HavingCondition havingCondition ->
                    new OracleHavingCondition(aliasConditionCompiler.compile(havingCondition, parameterBinder), WRAPPED);
            case MultipleHaving multipleHaving -> multipleHavingCompiler.compile(multipleHaving, this, parameterBinder);
        };
    }

    private static String compileRaw(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("raw having condition must not be null or blank");
        }
        return raw.trim();
    }

}
