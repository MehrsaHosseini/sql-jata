package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.services.type.provider;

import ir.mohaymen.querygenerator.domain.schema.enumeration.JoinType;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.model.OracleJoinSyntax;

public interface OracleJoinTypeProvider {

    OracleJoinSyntax provide(JoinType joinType);

}
