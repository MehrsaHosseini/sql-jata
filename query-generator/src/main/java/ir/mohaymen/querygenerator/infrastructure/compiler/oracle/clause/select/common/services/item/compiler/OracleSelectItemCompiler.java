package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.select.common.services.item.compiler;

import ir.mohaymen.querygenerator.domain.schema.column.Column;

public interface OracleSelectItemCompiler {

    String compile(Column column);

}
