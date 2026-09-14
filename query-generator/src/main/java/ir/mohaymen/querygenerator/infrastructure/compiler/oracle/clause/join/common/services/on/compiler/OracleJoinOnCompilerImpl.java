package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.services.on.compiler;

import ir.mohaymen.querygenerator.domain.join.JoinOn;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.column.OracleColumnReferenceCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.column.OracleColumnReferenceCompilerImpl;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class OracleJoinOnCompilerImpl implements OracleJoinOnCompiler {

    private static final String CONDITION_SEPARATOR = " AND ";
    private static final String OPERAND_SEPARATOR = " ";

    private final OracleColumnReferenceCompiler columnReferenceCompiler;

    public OracleJoinOnCompilerImpl() {
        this(new OracleColumnReferenceCompilerImpl());
    }

    public OracleJoinOnCompilerImpl(OracleColumnReferenceCompiler columnReferenceCompiler) {
        this.columnReferenceCompiler = Objects.requireNonNull(columnReferenceCompiler, "column reference compiler must not be null");
    }

    @Override
    public String compile(List<JoinOn> onList) {
        if (onList == null || onList.isEmpty()) {
            throw new IllegalArgumentException("join on condition must not be null or empty");
        }

        StringJoiner conditions = new StringJoiner(CONDITION_SEPARATOR);
        for (JoinOn on : onList) {
            conditions.add(compileCondition(on));
        }
        return conditions.toString();
    }

    private String compileCondition(JoinOn on) {
        if (on == null) {
            throw new IllegalArgumentException("join on condition must not be null");
        }

        return columnReferenceCompiler.compile(on.leftColumn())
                + OPERAND_SEPARATOR + requireOperation(on.operation())
                + OPERAND_SEPARATOR + columnReferenceCompiler.compile(on.rightColumn());
    }

    private static String requireOperation(String operation) {
        if (operation == null || operation.isBlank()) {
            throw new IllegalArgumentException("join on operation must not be null or blank");
        }
        return operation.trim();
    }

}
