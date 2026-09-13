package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.services.source.compiler;

import ir.mohaymen.querygenerator.domain.join.Join;
import ir.mohaymen.querygenerator.domain.join.JoinItem;
import ir.mohaymen.querygenerator.domain.join.JoinList;
import ir.mohaymen.querygenerator.domain.join.JoinRaw;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.services.item.compiler.OracleJoinItemCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.services.item.compiler.OracleJoinItemCompilerImpl;

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
    public String compile(Join join) {
        return switch (join) {
            case null -> EMPTY_CLAUSE;
            case JoinRaw joinRaw -> compileRaw(joinRaw.raw());
            case JoinItem joinItem -> joinItemCompiler.compile(joinItem);
            case JoinList joinList -> compileList(joinList.joinList());
        };
    }

    /**
     * Joins are chained by whitespace only, because every join already carries its own keyword.
     */
    private String compileList(List<Join> joins) {
        if (joins == null || joins.isEmpty()) {
            return EMPTY_CLAUSE;
        }

        StringJoiner chain = new StringJoiner(JOIN_SEPARATOR);
        for (Join join : joins) {
            String compiled = compile(join);
            if (!compiled.isEmpty()) {
                chain.add(compiled);
            }
        }
        return chain.toString();
    }

    private String compileRaw(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("raw join expression must not be null or blank");
        }
        return raw.trim();
    }

}
