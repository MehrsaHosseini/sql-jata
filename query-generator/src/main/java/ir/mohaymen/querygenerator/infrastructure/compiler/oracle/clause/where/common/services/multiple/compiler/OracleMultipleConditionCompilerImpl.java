package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.multiple.compiler;

import ir.mohaymen.querygenerator.domain.where.MultipleWhere;
import ir.mohaymen.querygenerator.domain.where.Where;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.condition.compiler.OracleConditionCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class OracleMultipleConditionCompilerImpl implements OracleMultipleConditionCompiler {

    private static final String AND_SEPARATOR = " AND ";
    private static final String OR_SEPARATOR = " OR ";
    private static final String GROUP_PREFIX = "(";
    private static final String GROUP_SUFFIX = ")";

    @Override
    public String compile(MultipleWhere multipleWhere,
                          OracleConditionCompiler conditionCompiler,
                          OracleParameterBinder parameterBinder) {
        if (multipleWhere == null) {
            throw new IllegalArgumentException("multiple condition must not be null");
        }
        Objects.requireNonNull(conditionCompiler, "condition compiler must not be null");

        List<Where> leftOperand = multipleWhere.leftOperand();
        List<Where> rightOperand = multipleWhere.rightOperand();
        if (isAbsent(leftOperand) && isAbsent(rightOperand)) {
            throw new IllegalArgumentException("a multiple condition needs at least one operand");
        }
        if (isAbsent(rightOperand)) {
            return compileOperand(leftOperand, conditionCompiler, parameterBinder);
        }
        if (isAbsent(leftOperand)) {
            return compileOperand(rightOperand, conditionCompiler, parameterBinder);
        }
        return compileOperand(leftOperand, conditionCompiler, parameterBinder)
                + OR_SEPARATOR
                + compileOperand(rightOperand, conditionCompiler, parameterBinder);
    }

    private static String compileOperand(List<Where> operand,
                                         OracleConditionCompiler conditionCompiler,
                                         OracleParameterBinder parameterBinder) {
        boolean joinedByAnd = operand.size() > 1;
        StringJoiner conditions = new StringJoiner(AND_SEPARATOR, GROUP_PREFIX, GROUP_SUFFIX);
        for (Where condition : operand) {
            conditions.add(compileMember(condition, joinedByAnd, conditionCompiler, parameterBinder));
        }
        return conditions.toString();
    }


    private static String compileMember(Where condition,
                                        boolean joinedByAnd,
                                        OracleConditionCompiler conditionCompiler,
                                        OracleParameterBinder parameterBinder) {
        String compiled = conditionCompiler.compile(condition, parameterBinder);
        return joinedByAnd && producesOr(condition)
                ? GROUP_PREFIX + compiled + GROUP_SUFFIX
                : compiled;
    }

    private static boolean producesOr(Where condition) {
        return condition instanceof MultipleWhere multipleWhere
                && !isAbsent(multipleWhere.leftOperand())
                && !isAbsent(multipleWhere.rightOperand());
    }

    private static boolean isAbsent(List<Where> operand) {
        return operand == null || operand.isEmpty();
    }

}
