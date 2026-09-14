package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.delete.common.services.source.compiler;

import ir.mohaymen.querygenerator.domain.delete.Delete;
import ir.mohaymen.querygenerator.domain.delete.DeleteAll;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.table.OracleTableReferenceCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.table.OracleTableReferenceCompilerImpl;

import java.util.Objects;

public class OracleDeleteSourceCompilerImpl implements OracleDeleteSourceCompiler {

    private static final String DELETE_FROM_KEYWORD = "DELETE FROM ";

    private final OracleTableReferenceCompiler tableReferenceCompiler;

    public OracleDeleteSourceCompilerImpl() {
        this(new OracleTableReferenceCompilerImpl());
    }

    public OracleDeleteSourceCompilerImpl(OracleTableReferenceCompiler tableReferenceCompiler) {
        this.tableReferenceCompiler = Objects.requireNonNull(tableReferenceCompiler, "table reference compiler must not be null");
    }

    @Override
    public String compile(Delete delete) {
        return switch (delete) {
            case null -> throw new IllegalArgumentException("delete must not be null");
            case DeleteAll deleteAll -> DELETE_FROM_KEYWORD + tableReferenceCompiler.compile(deleteAll.table());
        };
    }

}
