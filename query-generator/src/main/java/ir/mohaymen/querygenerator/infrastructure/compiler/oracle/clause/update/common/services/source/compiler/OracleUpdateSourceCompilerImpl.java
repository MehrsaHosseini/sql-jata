package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.update.common.services.source.compiler;

import ir.mohaymen.querygenerator.domain.update.Update;
import ir.mohaymen.querygenerator.domain.update.UpdateAll;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.update.common.services.assignment.compiler.OracleUpdateAssignmentCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.update.common.services.assignment.compiler.OracleUpdateAssignmentCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.table.OracleTableReferenceCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.table.OracleTableReferenceCompilerImpl;

import java.util.Objects;

public class OracleUpdateSourceCompilerImpl implements OracleUpdateSourceCompiler {

    private static final String UPDATE_KEYWORD = "UPDATE ";
    private static final String SET_KEYWORD = " SET ";

    private final OracleTableReferenceCompiler tableReferenceCompiler;
    private final OracleUpdateAssignmentCompiler updateAssignmentCompiler;

    public OracleUpdateSourceCompilerImpl() {
        this(new OracleTableReferenceCompilerImpl(), new OracleUpdateAssignmentCompilerImpl());
    }

    public OracleUpdateSourceCompilerImpl(OracleTableReferenceCompiler tableReferenceCompiler,
                                          OracleUpdateAssignmentCompiler updateAssignmentCompiler) {
        this.tableReferenceCompiler = Objects.requireNonNull(tableReferenceCompiler, "table reference compiler must not be null");
        this.updateAssignmentCompiler = Objects.requireNonNull(updateAssignmentCompiler, "update assignment compiler must not be null");
    }

    @Override
    public String compile(Update update, OracleParameterBinder parameterBinder) {
        Objects.requireNonNull(parameterBinder, "parameter binder must not be null");

        return switch (update) {
            case null -> throw new IllegalArgumentException("update must not be null");
            case UpdateAll updateAll -> compileAll(updateAll, parameterBinder);
        };
    }

    private String compileAll(UpdateAll updateAll, OracleParameterBinder parameterBinder) {
        return UPDATE_KEYWORD
                + tableReferenceCompiler.compile(updateAll.table())
                + SET_KEYWORD
                + updateAssignmentCompiler.compile(updateAll.setList(), parameterBinder);
    }

}
