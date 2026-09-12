package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.from.common.services.table.compiler;

import ir.mohaymen.querygenerator.domain.schema.table.Table;

public interface OracleFromTableCompiler {

    String compile(Table table);

}
