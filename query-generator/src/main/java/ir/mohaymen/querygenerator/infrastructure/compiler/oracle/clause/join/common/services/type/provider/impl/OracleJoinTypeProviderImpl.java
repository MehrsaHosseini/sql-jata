package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.services.type.provider.impl;

import ir.mohaymen.querygenerator.domain.schema.enumeration.JoinType;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.model.OracleJoinSyntax;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.services.type.provider.OracleJoinTypeProvider;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Objects;

public class OracleJoinTypeProviderImpl implements OracleJoinTypeProvider {

    private final Map<JoinType, OracleJoinSyntax> syntaxes;

    public OracleJoinTypeProviderImpl() {
        this(defaultSyntaxes());
    }

    public OracleJoinTypeProviderImpl(Map<JoinType, OracleJoinSyntax> syntaxes) {
        Objects.requireNonNull(syntaxes, "join syntaxes must not be null");
        this.syntaxes = new EnumMap<>(syntaxes);
        requireEveryJoinTypeCovered();
    }

    @Override
    public OracleJoinSyntax provide(JoinType joinType) {
        if (joinType == null) {
            throw new IllegalArgumentException("join type must not be null");
        }
        return syntaxes.get(joinType);
    }

    private static Map<JoinType, OracleJoinSyntax> defaultSyntaxes() {
        Map<JoinType, OracleJoinSyntax> defaults = new EnumMap<>(JoinType.class);
        defaults.put(JoinType.INNER, new OracleJoinSyntax("INNER JOIN", true));
        defaults.put(JoinType.LEFT, new OracleJoinSyntax("LEFT OUTER JOIN", true));
        defaults.put(JoinType.RIGHT, new OracleJoinSyntax("RIGHT OUTER JOIN", true));
        defaults.put(JoinType.FULL, new OracleJoinSyntax("FULL OUTER JOIN", true));
        defaults.put(JoinType.CROSS, new OracleJoinSyntax("CROSS JOIN", false));
        return defaults;
    }

    private void requireEveryJoinTypeCovered() {
        EnumSet<JoinType> missing = EnumSet.allOf(JoinType.class);
        missing.removeAll(syntaxes.keySet());
        if (!missing.isEmpty()) {
            throw new IllegalArgumentException("no oracle join syntax registered for: " + missing);
        }
    }

}
