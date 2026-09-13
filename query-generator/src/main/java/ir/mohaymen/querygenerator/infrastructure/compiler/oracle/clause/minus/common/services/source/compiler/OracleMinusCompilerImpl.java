package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.minus.common.services.source.compiler;

import ir.mohaymen.querygenerator.domain.minus.Minus;
import ir.mohaymen.querygenerator.domain.minus.MinusDistinct;

public class OracleMinusCompilerImpl implements OracleMinusCompiler {

    private static final String MINUS_KEYWORD = "MINUS";

    @Override
    public String compile(Minus minus) {
        return switch (minus) {
            case null -> throw new IllegalArgumentException("minus must not be null");
            case MinusDistinct ignored -> MINUS_KEYWORD;
        };
    }

}
