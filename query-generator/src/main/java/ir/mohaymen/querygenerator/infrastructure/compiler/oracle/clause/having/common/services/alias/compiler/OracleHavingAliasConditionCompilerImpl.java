package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.common.services.alias.compiler;

import ir.mohaymen.querygenerator.domain.having.HavingCondition;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.identifier.OracleIdentifierQuoter;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.identifier.OracleIdentifierQuoterImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.predicate.OraclePredicateCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.predicate.OraclePredicateCompilerImpl;

import java.util.Objects;

public class OracleHavingAliasConditionCompilerImpl implements OracleHavingAliasConditionCompiler {

    private final OracleIdentifierQuoter identifierQuoter;
    private final OraclePredicateCompiler predicateCompiler;

    public OracleHavingAliasConditionCompilerImpl() {
        this(new OracleIdentifierQuoterImpl(), new OraclePredicateCompilerImpl());
    }

    public OracleHavingAliasConditionCompilerImpl(OracleIdentifierQuoter identifierQuoter,
                                                 OraclePredicateCompiler predicateCompiler) {
        this.identifierQuoter = Objects.requireNonNull(identifierQuoter, "identifier quoter must not be null");
        this.predicateCompiler = Objects.requireNonNull(predicateCompiler, "predicate compiler must not be null");
    }

    @Override
    public String compile(HavingCondition havingCondition, OracleParameterBinder parameterBinder) {
        if (havingCondition == null) {
            throw new IllegalArgumentException("having condition must not be null");
        }

        String alias = havingCondition.alias();
        if (alias == null || alias.isBlank()) {
            throw new IllegalArgumentException("having condition alias must not be null or blank");
        }

        return predicateCompiler.compile(identifierQuoter.quote(alias),
                havingCondition.operation(), havingCondition.value(), parameterBinder);
    }

}
