package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.services.item.compiler;

import ir.mohaymen.querygenerator.domain.join.JoinItem;

public interface OracleJoinItemCompiler {

    String compile(JoinItem joinItem);

}
