package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.services.on.compiler;

import ir.mohaymen.querygenerator.domain.join.JoinOn;

import java.util.List;

public interface OracleJoinOnCompiler {

    String compile(List<JoinOn> onList);

}
