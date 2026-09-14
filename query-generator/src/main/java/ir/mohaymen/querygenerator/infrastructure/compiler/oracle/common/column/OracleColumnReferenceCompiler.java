package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.column;

import ir.mohaymen.querygenerator.domain.schema.column.Column;

public interface OracleColumnReferenceCompiler {

    String compile(Column column);

}
