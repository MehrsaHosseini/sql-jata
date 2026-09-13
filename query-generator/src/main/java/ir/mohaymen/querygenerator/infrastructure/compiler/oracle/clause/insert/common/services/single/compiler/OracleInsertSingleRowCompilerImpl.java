package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.single.compiler;

import ir.mohaymen.querygenerator.domain.insert.InsertSingleRow;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.target.compiler.OracleInsertTargetCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.target.compiler.OracleInsertTargetCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

import java.util.Objects;

public class OracleInsertSingleRowCompilerImpl implements OracleInsertSingleRowCompiler {

    private static final String INSERT_INTO_KEYWORD = "INSERT INTO ";

    private final OracleInsertTargetCompiler insertTargetCompiler;

    public OracleInsertSingleRowCompilerImpl() {
        this(new OracleInsertTargetCompilerImpl());
    }

    public OracleInsertSingleRowCompilerImpl(OracleInsertTargetCompiler insertTargetCompiler) {
        this.insertTargetCompiler = Objects.requireNonNull(insertTargetCompiler, "insert target compiler must not be null");
    }

    @Override
    public String compile(InsertSingleRow insert, OracleParameterBinder parameterBinder) {
        if (insert == null) {
            throw new IllegalArgumentException("insert single row must not be null");
        }
        Objects.requireNonNull(parameterBinder, "parameter binder must not be null");

        return INSERT_INTO_KEYWORD + insertTargetCompiler.compile(insert, parameterBinder);
    }

}
