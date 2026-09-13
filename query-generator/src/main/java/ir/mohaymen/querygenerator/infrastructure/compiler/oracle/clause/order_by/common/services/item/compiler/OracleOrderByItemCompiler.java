package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.order_by.common.services.item.compiler;

import ir.mohaymen.querygenerator.domain.schema.column.Column;

public interface OracleOrderByItemCompiler {

    String compile(Column column);

}
