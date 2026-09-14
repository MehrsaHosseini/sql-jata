package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.where.common.services.nullable.compiler;

import ir.mohaymen.querygenerator.domain.where.WhereNull;

public interface OracleNullConditionCompiler {

    String compile(WhereNull whereNull);

}
