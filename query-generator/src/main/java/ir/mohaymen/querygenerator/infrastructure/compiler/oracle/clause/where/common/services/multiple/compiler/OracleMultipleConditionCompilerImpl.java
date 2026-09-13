package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.multiple.compiler;

import ir.mohaymen.querygenerator.domain.where.MultipleWhere;
import ir.mohaymen.querygenerator.domain.where.Where;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.condition.compiler.OracleConditionCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class OracleMultipleConditionCompilerImpl implements OracleMultipleConditionCompiler {

    private final String AND_SEPARATOR = " AND ";
    private final String OR_SEPARATOR = " OR ";
    private final String GROUP_PREFIX = "(";
    private final String GROUP_SUFFIX = ")";
    private final String LEFT_OPERAND = "left";
    private final String RIGHT_OPERAND = "right";

    @Override
    public String compile(MultipleWhere multipleWhere,
                          OracleConditionCompiler conditionCompiler,
                          OracleParameterBinder parameterBinder) {
        if (multipleWhere == null) {
            throw new IllegalArgumentException("multiple condition must not be null");
        }
        Objects.requireNonNull(conditionCompiler, "condition compiler must not be null");

        return compileOperand(multipleWhere.leftOperand(), LEFT_OPERAND, conditionCompiler, parameterBinder)
                + OR_SEPARATOR
                + compileOperand(multipleWhere.rightOperand(), RIGHT_OPERAND, conditionCompiler, parameterBinder);
    }

    private String compileOperand(List<Where> operand,
                                         String operandName,
                                         OracleConditionCompiler conditionCompiler,
                                         OracleParameterBinder parameterBinder) {
        if (operand == null || operand.isEmpty()) {
            throw new IllegalArgumentException("the " + operandName + " operand of a multiple condition must not be null or empty");
        }

        boolean joinedByAnd = operand.size() > 1;
        StringJoiner conditions = new StringJoiner(AND_SEPARATOR, GROUP_PREFIX, GROUP_SUFFIX);
        for (Where condition : operand) {
            conditions.add(compileMember(condition, joinedByAnd, conditionCompiler, parameterBinder));
        }
        return conditions.toString();
    }

    private String compileMember(Where condition,
                                        boolean joinedByAnd,
                                        OracleConditionCompiler conditionCompiler,
                                        OracleParameterBinder parameterBinder) {
        String compiled = conditionCompiler.compile(condition, parameterBinder);
        return joinedByAnd && condition instanceof MultipleWhere
                ? GROUP_PREFIX + compiled + GROUP_SUFFIX
                : compiled;
    }

}
