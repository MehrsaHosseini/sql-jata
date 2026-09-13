package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.table;

import ir.mohaymen.querygenerator.domain.schema.table.Table;

public interface OracleTableReferenceCompiler {

    String compile(Table table);

}
