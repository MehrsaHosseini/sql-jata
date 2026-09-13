package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.from.common.services.source.compiler;

import ir.mohaymen.querygenerator.domain.from.From;
import ir.mohaymen.querygenerator.domain.from.FromRaw;
import ir.mohaymen.querygenerator.domain.from.FromTable;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.table.OracleTableReferenceCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.table.OracleTableReferenceCompilerImpl;

import java.util.Objects;

public class OracleFromSourceCompilerImpl implements OracleFromSourceCompiler {

    private final String DUAL_TABLE = "DUAL";

    private final OracleTableReferenceCompiler tableReferenceCompiler;

    public OracleFromSourceCompilerImpl() {
        this(new OracleTableReferenceCompilerImpl());
    }

    public OracleFromSourceCompilerImpl(OracleTableReferenceCompiler tableReferenceCompiler) {
        this.tableReferenceCompiler = Objects.requireNonNull(tableReferenceCompiler, "table reference compiler must not be null");
    }

    @Override
    public String compile(From from) {
        return switch (from) {
            case null -> DUAL_TABLE;
            case FromRaw fromRaw -> compileRaw(fromRaw.raw());
            case FromTable fromTable -> tableReferenceCompiler.compile(fromTable.table());
        };
    }

    private String compileRaw(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("raw from expression must not be null or blank");
        }
        return raw.trim();
    }

}
