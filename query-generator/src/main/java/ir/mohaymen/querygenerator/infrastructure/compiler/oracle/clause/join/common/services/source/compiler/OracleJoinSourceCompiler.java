package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.join.common.services.source.compiler;

import ir.mohaymen.querygenerator.domain.join.Join;

public interface OracleJoinSourceCompiler {

    String compile(Join join);

}
