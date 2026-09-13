package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.all.compiler;

import ir.mohaymen.querygenerator.domain.insert.InsertAll;
import ir.mohaymen.querygenerator.domain.insert.InsertSingleRow;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.target.compiler.OracleInsertTargetCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.target.compiler.OracleInsertTargetCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class OracleInsertAllCompilerImpl implements OracleInsertAllCompiler {

    private static final String INSERT_ALL_KEYWORD = "INSERT ALL ";
    private static final String INTO_KEYWORD = "INTO ";
    private static final String ROW_SEPARATOR = " ";
    private static final String DUAL_SOURCE = "SELECT * FROM DUAL";

    private final OracleInsertTargetCompiler insertTargetCompiler;

    public OracleInsertAllCompilerImpl() {
        this(new OracleInsertTargetCompilerImpl());
    }

    public OracleInsertAllCompilerImpl(OracleInsertTargetCompiler insertTargetCompiler) {
        this.insertTargetCompiler = Objects.requireNonNull(insertTargetCompiler, "insert target compiler must not be null");
    }

    @Override
    public String compile(InsertAll insert, OracleParameterBinder parameterBinder) {
        if (insert == null) {
            throw new IllegalArgumentException("insert all must not be null");
        }
        Objects.requireNonNull(parameterBinder, "parameter binder must not be null");

        List<InsertSingleRow> insertList = insert.insertList();
        if (insertList == null || insertList.isEmpty()) {
            throw new IllegalArgumentException("insert all rows must not be null or empty");
        }

        StringJoiner rows = new StringJoiner(ROW_SEPARATOR);
        for (InsertSingleRow row : insertList) {
            if (row == null) {
                throw new IllegalArgumentException("insert all row must not be null");
            }
            rows.add(INTO_KEYWORD + insertTargetCompiler.compile(row, parameterBinder));
        }
        return INSERT_ALL_KEYWORD + rows + ROW_SEPARATOR + DUAL_SOURCE;
    }

}
