package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.source.compiler;

import ir.mohaymen.querygenerator.domain.insert.Insert;
import ir.mohaymen.querygenerator.domain.insert.InsertAll;
import ir.mohaymen.querygenerator.domain.insert.InsertSingleRow;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.all.compiler.OracleInsertAllCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.all.compiler.OracleInsertAllCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.single.compiler.OracleInsertSingleRowCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.single.compiler.OracleInsertSingleRowCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

import java.util.Objects;

public class OracleInsertSourceCompilerImpl implements OracleInsertSourceCompiler {

    private final OracleInsertSingleRowCompiler insertSingleRowCompiler;
    private final OracleInsertAllCompiler insertAllCompiler;

    public OracleInsertSourceCompilerImpl() {
        this(new OracleInsertSingleRowCompilerImpl(), new OracleInsertAllCompilerImpl());
    }

    public OracleInsertSourceCompilerImpl(OracleInsertSingleRowCompiler insertSingleRowCompiler,
                                          OracleInsertAllCompiler insertAllCompiler) {
        this.insertSingleRowCompiler = Objects.requireNonNull(insertSingleRowCompiler, "insert single row compiler must not be null");
        this.insertAllCompiler = Objects.requireNonNull(insertAllCompiler, "insert all compiler must not be null");
    }

    @Override
    public String compile(Insert insert, OracleParameterBinder parameterBinder) {
        Objects.requireNonNull(parameterBinder, "parameter binder must not be null");

        return switch (insert) {
            case null -> throw new IllegalArgumentException("insert must not be null");
            case InsertSingleRow insertSingleRow -> insertSingleRowCompiler.compile(insertSingleRow, parameterBinder);
            case InsertAll insertAll -> insertAllCompiler.compile(insertAll, parameterBinder);
        };
    }

}
