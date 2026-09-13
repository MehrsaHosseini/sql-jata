package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.datepart.compiler;

import ir.mohaymen.querygenerator.domain.where.WhereDatePart;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.datepart.expression.OracleDatePartExpressionCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.datepart.expression.OracleDatePartExpressionCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.predicate.OraclePredicateCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.predicate.OraclePredicateCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.column.OracleColumnReferenceCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.column.OracleColumnReferenceCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

import java.util.Objects;

public class OracleDatePartConditionCompilerImpl implements OracleDatePartConditionCompiler {

    private final OracleColumnReferenceCompiler columnReferenceCompiler;
    private final OracleDatePartExpressionCompiler datePartExpressionCompiler;
    private final OraclePredicateCompiler predicateCompiler;

    public OracleDatePartConditionCompilerImpl() {
        this(new OracleColumnReferenceCompilerImpl(),
                new OracleDatePartExpressionCompilerImpl(),
                new OraclePredicateCompilerImpl());
    }

    public OracleDatePartConditionCompilerImpl(OracleColumnReferenceCompiler columnReferenceCompiler,
                                               OracleDatePartExpressionCompiler datePartExpressionCompiler,
                                               OraclePredicateCompiler predicateCompiler) {
        this.columnReferenceCompiler = Objects.requireNonNull(columnReferenceCompiler, "column reference compiler must not be null");
        this.datePartExpressionCompiler = Objects.requireNonNull(datePartExpressionCompiler, "date part expression compiler must not be null");
        this.predicateCompiler = Objects.requireNonNull(predicateCompiler, "predicate compiler must not be null");
    }

    @Override
    public String compile(WhereDatePart whereDatePart, OracleParameterBinder parameterBinder) {
        if (whereDatePart == null) {
            throw new IllegalArgumentException("date part condition must not be null");
        }

        String expression = datePartExpressionCompiler.compile(
                columnReferenceCompiler.compile(whereDatePart.column()), whereDatePart.datePart());

        return predicateCompiler.compile(expression, whereDatePart.operation(), whereDatePart.value(), parameterBinder);
    }

}
