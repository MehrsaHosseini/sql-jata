package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.common.services.multiple.compiler;

import ir.mohaymen.querygenerator.domain.having.Having;
import ir.mohaymen.querygenerator.domain.having.MultipleHaving;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.common.model.OracleHavingCondition;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.having.common.services.condition.compiler.OracleHavingConditionCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class OracleMultipleHavingCompilerImpl implements OracleMultipleHavingCompiler {

    private final String AND_SEPARATOR = " AND ";
    private final String OR_SEPARATOR = " OR ";
    private final String GROUP_PREFIX = "(";
    private final String GROUP_SUFFIX = ")";

    @Override
    public OracleHavingCondition compile(MultipleHaving multipleHaving,
                                         OracleHavingConditionCompiler conditionCompiler,
                                         OracleParameterBinder parameterBinder) {
        if (multipleHaving == null) {
            throw new IllegalArgumentException("multiple having condition must not be null");
        }
        Objects.requireNonNull(conditionCompiler, "having condition compiler must not be null");

        List<Having> leftOperand = multipleHaving.leftOperand();
        List<Having> rightOperand = multipleHaving.rightOperand();
        if (isAbsent(leftOperand) && isAbsent(rightOperand)) {
            throw new IllegalArgumentException("a multiple having condition needs at least one operand");
        }
        if (isAbsent(rightOperand)) {
            return compileOperand(leftOperand, conditionCompiler, parameterBinder);
        }
        if (isAbsent(leftOperand)) {
            return compileOperand(rightOperand, conditionCompiler, parameterBinder);
        }

        OracleHavingCondition left = compileOperand(leftOperand, conditionCompiler, parameterBinder);
        OracleHavingCondition right = compileOperand(rightOperand, conditionCompiler, parameterBinder);
        return new OracleHavingCondition(left.condition() + OR_SEPARATOR + right.condition(),
                sameWrapping(left.requiresWrapper(), right.requiresWrapper()));
    }

    private OracleHavingCondition compileOperand(List<Having> operand,
                                                        OracleHavingConditionCompiler conditionCompiler,
                                                        OracleParameterBinder parameterBinder) {
        boolean joinedByAnd = operand.size() > 1;
        Boolean requiresWrapper = null;

        StringJoiner conditions = new StringJoiner(AND_SEPARATOR, GROUP_PREFIX, GROUP_SUFFIX);
        for (Having condition : operand) {
            OracleHavingCondition compiled = conditionCompiler.compile(condition, parameterBinder);
            requiresWrapper = requiresWrapper == null
                    ? compiled.requiresWrapper()
                    : sameWrapping(requiresWrapper, compiled.requiresWrapper());
            conditions.add(member(compiled.condition(), condition, joinedByAnd));
        }
        return new OracleHavingCondition(conditions.toString(), requiresWrapper);
    }

    /**
     * A nested OR only needs its own parentheses next to an AND, otherwise the parentheses of the
     * enclosing operand already isolate it.
     */
    private String member(String compiled, Having condition, boolean joinedByAnd) {
        return joinedByAnd && producesOr(condition)
                ? GROUP_PREFIX + compiled + GROUP_SUFFIX
                : compiled;
    }

    private boolean producesOr(Having condition) {
        return condition instanceof MultipleHaving multipleHaving
                && !isAbsent(multipleHaving.leftOperand())
                && !isAbsent(multipleHaving.rightOperand());
    }

    private boolean sameWrapping(boolean first, boolean second) {
        if (first != second) {
            throw new IllegalArgumentException(
                    "a having condition must not mix an alias filter, which wraps the query, with an aggregate expression, which stays inside it");
        }
        return first;
    }

    private boolean isAbsent(List<Having> operand) {
        return operand == null || operand.isEmpty();
    }

}
