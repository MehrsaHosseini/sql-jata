package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.from.common.services.source.compiler;

import ir.mohaymen.querygenerator.domain.from.From;
import ir.mohaymen.querygenerator.domain.from.FromRaw;
import ir.mohaymen.querygenerator.domain.from.FromTable;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.from.common.services.table.compiler.OracleFromTableCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.from.common.services.table.compiler.OracleFromTableCompilerImpl;

import java.util.Objects;

public class OracleFromSourceCompilerImpl implements OracleFromSourceCompiler {

    private final String DUAL_TABLE = "DUAL";

    private final OracleFromTableCompiler fromTableCompiler;

    public OracleFromSourceCompilerImpl() {
        this(new OracleFromTableCompilerImpl());
    }

    public OracleFromSourceCompilerImpl(OracleFromTableCompiler fromTableCompiler) {
        this.fromTableCompiler = Objects.requireNonNull(fromTableCompiler, "from table compiler must not be null");
    }

    @Override
    public String compile(From from) {
        return switch (from) {
            case null -> DUAL_TABLE;
            case FromRaw fromRaw -> compileRaw(fromRaw.raw());
            case FromTable fromTable -> fromTableCompiler.compile(fromTable.table());
        };
    }

    private String compileRaw(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("raw from expression must not be null or blank");
        }
        return raw.trim();
    }

}
