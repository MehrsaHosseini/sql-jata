package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.services.item.compiler;

import ir.mohaymen.querygenerator.domain.join.JoinItem;
import ir.mohaymen.querygenerator.domain.join.JoinOn;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.model.OracleJoinSyntax;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.services.on.compiler.OracleJoinOnCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.services.on.compiler.OracleJoinOnCompilerImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.services.type.provider.OracleJoinTypeProvider;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.services.type.provider.impl.OracleJoinTypeProviderImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.table.OracleTableReferenceCompiler;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.table.OracleTableReferenceCompilerImpl;

import java.util.List;
import java.util.Objects;

public class OracleJoinItemCompilerImpl implements OracleJoinItemCompiler {

    private static final String PART_SEPARATOR = " ";
    private static final String ON_KEYWORD = "ON ";

    private final OracleJoinTypeProvider joinTypeProvider;
    private final OracleTableReferenceCompiler tableReferenceCompiler;
    private final OracleJoinOnCompiler joinOnCompiler;

    public OracleJoinItemCompilerImpl() {
        this(new OracleJoinTypeProviderImpl(),
                new OracleTableReferenceCompilerImpl(),
                new OracleJoinOnCompilerImpl());
    }

    public OracleJoinItemCompilerImpl(OracleJoinTypeProvider joinTypeProvider,
                                      OracleTableReferenceCompiler tableReferenceCompiler,
                                      OracleJoinOnCompiler joinOnCompiler) {
        this.joinTypeProvider = Objects.requireNonNull(joinTypeProvider, "join type provider must not be null");
        this.tableReferenceCompiler = Objects.requireNonNull(tableReferenceCompiler, "table reference compiler must not be null");
        this.joinOnCompiler = Objects.requireNonNull(joinOnCompiler, "join on compiler must not be null");
    }

    @Override
    public String compile(JoinItem joinItem) {
        if (joinItem == null) {
            throw new IllegalArgumentException("join item must not be null");
        }

        OracleJoinSyntax syntax = joinTypeProvider.provide(joinItem.joinType());
        String join = syntax.keyword() + PART_SEPARATOR + tableReferenceCompiler.compile(joinItem.table());

        List<JoinOn> onList = joinItem.onList();
        if (!syntax.supportsCondition()) {
            if (onList != null && !onList.isEmpty()) {
                throw new IllegalArgumentException(syntax.keyword() + " must not have an on condition");
            }
            return join;
        }
        if (onList == null || onList.isEmpty()) {
            throw new IllegalArgumentException(syntax.keyword() + " needs at least one on condition");
        }
        return join + PART_SEPARATOR + ON_KEYWORD + joinOnCompiler.compile(onList);
    }

}
