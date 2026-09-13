package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.delete.common.services.source.compiler;

import ir.mohaymen.querygenerator.domain.delete.Delete;

public interface OracleDeleteSourceCompiler {

    String compile(Delete delete);

}
