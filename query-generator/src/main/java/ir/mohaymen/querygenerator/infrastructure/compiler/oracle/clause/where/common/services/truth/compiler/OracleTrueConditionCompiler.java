package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.truth.compiler;

import ir.mohaymen.querygenerator.domain.where.WhereTrue;

public interface OracleTrueConditionCompiler {

    String compile(WhereTrue whereTrue);

}
