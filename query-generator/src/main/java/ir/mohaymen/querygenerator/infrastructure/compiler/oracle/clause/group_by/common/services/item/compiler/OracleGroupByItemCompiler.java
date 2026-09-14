package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.group_by.common.services.item.compiler;

import ir.mohaymen.querygenerator.domain.schema.column.Column;

public interface OracleGroupByItemCompiler {

    String compile(Column column);

}
