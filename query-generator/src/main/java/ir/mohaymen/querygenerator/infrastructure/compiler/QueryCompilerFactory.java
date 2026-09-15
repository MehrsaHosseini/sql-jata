package ir.mohaymen.querygenerator.infrastructure.compiler;

import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.OracleQueryCompiler;


import java.util.Objects;

public final class QueryCompilerFactory {

    private QueryCompilerFactory() {
    }

    public static QueryCompiler create(QueryCompilerDialect dialect) {
        Objects.requireNonNull(dialect, "compiler dialect must not be null");
        return switch (dialect) {
            case ORACLE -> new OracleQueryCompiler();

        };
    }
}
