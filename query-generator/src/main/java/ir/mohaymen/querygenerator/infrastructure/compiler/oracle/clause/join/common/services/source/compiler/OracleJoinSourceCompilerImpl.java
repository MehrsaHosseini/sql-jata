package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.services.source.compiler;

import ir.mohaymen.querygenerator.domain.join.Join;
import ir.mohaymen.querygenerator.domain.join.JoinItem;
import ir.mohaymen.querygenerator.domain.join.JoinList;
import ir.mohaymen.querygenerator.domain.join.JoinRaw;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.services.item.compiler.OracleJoinItemCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.services.item.compiler.OracleJoinItemCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class OracleJoinSourceCompilerImpl implements OracleJoinSourceCompiler {

    private static final String JOIN_SEPARATOR = " ";
    private static final String EMPTY_CLAUSE = "";

    private final OracleJoinItemCompiler joinItemCompiler;

    public OracleJoinSourceCompilerImpl() {
        this(new OracleJoinItemCompilerImpl());
    }

    public OracleJoinSourceCompilerImpl(OracleJoinItemCompiler joinItemCompiler) {
        this.joinItemCompiler = Objects.requireNonNull(joinItemCompiler, "join item compiler must not be null");
    }

    @Override
    public String compile(Join join, OracleParameterBinder parameterBinder) {
        Objects.requireNonNull(parameterBinder, "parameter binder must not be null");
        return switch (join) {
            case null -> EMPTY_CLAUSE;
            case JoinRaw joinRaw -> compileRaw(joinRaw, parameterBinder);
            case JoinItem joinItem -> joinItemCompiler.compile(joinItem);
            case JoinList joinList -> compileList(joinList.joinList(), parameterBinder);
        };
    }

    /**
     * Joins are chained by whitespace only, because every join already carries its own keyword.
     */
    private String compileList(List<Join> joins, OracleParameterBinder parameterBinder) {
        if (joins == null || joins.isEmpty()) {
            return EMPTY_CLAUSE;
        }

        StringJoiner chain = new StringJoiner(JOIN_SEPARATOR);
        for (Join join : joins) {
            String compiled = compile(join, parameterBinder);
            if (!compiled.isEmpty()) {
                chain.add(compiled);
            }
        }
        return chain.toString();
    }

    private String compileRaw(JoinRaw joinRaw, OracleParameterBinder parameterBinder) {
        String raw = joinRaw.raw();
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("raw join expression must not be null or blank");
        }
        parameterBinder.putAll(joinRaw.parameters());
        return parameterBinder.renderSql(raw.trim());
    }

}
