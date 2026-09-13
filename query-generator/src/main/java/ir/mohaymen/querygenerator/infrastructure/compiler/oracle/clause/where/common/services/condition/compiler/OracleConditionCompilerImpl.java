package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.condition.compiler;

import ir.mohaymen.querygenerator.domain.where.BasicWhere;
import ir.mohaymen.querygenerator.domain.where.MultipleWhere;
import ir.mohaymen.querygenerator.domain.where.Where;
import ir.mohaymen.querygenerator.domain.where.WhereExists;
import ir.mohaymen.querygenerator.domain.where.WhereNull;
import ir.mohaymen.querygenerator.domain.where.WhereRaw;
import ir.mohaymen.querygenerator.domain.where.WhereTrue;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.basic.compiler.OracleBasicConditionCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.basic.compiler.OracleBasicConditionCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.exists.compiler.OracleExistsConditionCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.exists.compiler.OracleExistsConditionCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.multiple.compiler.OracleMultipleConditionCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.multiple.compiler.OracleMultipleConditionCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.nullable.compiler.OracleNullConditionCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.nullable.compiler.OracleNullConditionCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.truth.compiler.OracleTrueConditionCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.truth.compiler.OracleTrueConditionCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

import java.util.Objects;

public class OracleConditionCompilerImpl implements OracleConditionCompiler {

    private final OracleBasicConditionCompiler basicConditionCompiler;
    private final OracleNullConditionCompiler nullConditionCompiler;
    private final OracleTrueConditionCompiler trueConditionCompiler;
    private final OracleExistsConditionCompiler existsConditionCompiler;
    private final OracleMultipleConditionCompiler multipleConditionCompiler;

    public OracleConditionCompilerImpl() {
        this(new OracleBasicConditionCompilerImpl(),
                new OracleNullConditionCompilerImpl(),
                new OracleTrueConditionCompilerImpl(),
                new OracleExistsConditionCompilerImpl(),
                new OracleMultipleConditionCompilerImpl());
    }

    public OracleConditionCompilerImpl(OracleBasicConditionCompiler basicConditionCompiler,
                                       OracleNullConditionCompiler nullConditionCompiler,
                                       OracleTrueConditionCompiler trueConditionCompiler,
                                       OracleExistsConditionCompiler existsConditionCompiler,
                                       OracleMultipleConditionCompiler multipleConditionCompiler) {
        this.basicConditionCompiler = Objects.requireNonNull(basicConditionCompiler, "basic condition compiler must not be null");
        this.nullConditionCompiler = Objects.requireNonNull(nullConditionCompiler, "null condition compiler must not be null");
        this.trueConditionCompiler = Objects.requireNonNull(trueConditionCompiler, "true condition compiler must not be null");
        this.existsConditionCompiler = Objects.requireNonNull(existsConditionCompiler, "exists condition compiler must not be null");
        this.multipleConditionCompiler = Objects.requireNonNull(multipleConditionCompiler, "multiple condition compiler must not be null");
    }

    @Override
    public String compile(Where where, OracleParameterBinder parameterBinder) {
        Objects.requireNonNull(parameterBinder, "parameter binder must not be null");

        return switch (where) {
            case null -> throw new IllegalArgumentException("condition must not be null");
            case WhereRaw whereRaw -> compileRaw(whereRaw.raw());
            case BasicWhere basicWhere -> basicConditionCompiler.compile(basicWhere, parameterBinder);
            case WhereNull whereNull -> nullConditionCompiler.compile(whereNull);
            case WhereTrue whereTrue -> trueConditionCompiler.compile(whereTrue);
            case WhereExists whereExists -> existsConditionCompiler.compile(whereExists, this, parameterBinder);
            case MultipleWhere multipleWhere -> multipleConditionCompiler.compile(multipleWhere, this, parameterBinder);
        };
    }

    private static String compileRaw(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("raw condition must not be null or blank");
        }
        return raw.trim();
    }

}
