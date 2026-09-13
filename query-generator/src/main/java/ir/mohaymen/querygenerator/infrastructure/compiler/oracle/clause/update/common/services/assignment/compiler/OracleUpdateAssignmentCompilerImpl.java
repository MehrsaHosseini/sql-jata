package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.update.common.services.assignment.compiler;

import ir.mohaymen.querygenerator.domain.update.UpdateSet;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.update.common.services.item.compiler.OracleUpdateSetItemCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.update.common.services.item.compiler.OracleUpdateSetItemCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class OracleUpdateAssignmentCompilerImpl implements OracleUpdateAssignmentCompiler {

    private static final String ASSIGNMENT_SEPARATOR = ", ";

    private final OracleUpdateSetItemCompiler updateSetItemCompiler;

    public OracleUpdateAssignmentCompilerImpl() {
        this(new OracleUpdateSetItemCompilerImpl());
    }

    public OracleUpdateAssignmentCompilerImpl(OracleUpdateSetItemCompiler updateSetItemCompiler) {
        this.updateSetItemCompiler = Objects.requireNonNull(updateSetItemCompiler, "update set item compiler must not be null");
    }

    @Override
    public String compile(List<UpdateSet> setList, OracleParameterBinder parameterBinder) {
        if (setList == null || setList.isEmpty()) {
            throw new IllegalArgumentException("update set list must not be null or empty");
        }
        Objects.requireNonNull(parameterBinder, "parameter binder must not be null");

        StringJoiner assignments = new StringJoiner(ASSIGNMENT_SEPARATOR);
        for (UpdateSet updateSet : setList) {
            assignments.add(updateSetItemCompiler.compile(updateSet, parameterBinder));
        }
        return assignments.toString();
    }

}
