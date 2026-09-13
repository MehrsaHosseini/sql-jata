package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.basic.compiler;

import ir.mohaymen.querygenerator.domain.where.BasicWhere;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.predicate.compiler.OraclePredicateCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.predicate.compiler.OraclePredicateCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.column.OracleColumnReferenceCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.column.OracleColumnReferenceCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

import java.util.Objects;

public class OracleBasicConditionCompilerImpl implements OracleBasicConditionCompiler {

    private final OracleColumnReferenceCompiler columnReferenceCompiler;
    private final OraclePredicateCompiler predicateCompiler;

    public OracleBasicConditionCompilerImpl() {
        this(new OracleColumnReferenceCompilerImpl(), new OraclePredicateCompilerImpl());
    }

    public OracleBasicConditionCompilerImpl(OracleColumnReferenceCompiler columnReferenceCompiler,
                                            OraclePredicateCompiler predicateCompiler) {
        this.columnReferenceCompiler = Objects.requireNonNull(columnReferenceCompiler, "column reference compiler must not be null");
        this.predicateCompiler = Objects.requireNonNull(predicateCompiler, "predicate compiler must not be null");
    }

    @Override
    public String compile(BasicWhere basicWhere, OracleParameterBinder parameterBinder) {
        if (basicWhere == null) {
            throw new IllegalArgumentException("basic condition must not be null");
        }

        return predicateCompiler.compile(
                columnReferenceCompiler.compile(basicWhere.column()),
                basicWhere.operation(),
                basicWhere.value(),
                parameterBinder);
    }

}
