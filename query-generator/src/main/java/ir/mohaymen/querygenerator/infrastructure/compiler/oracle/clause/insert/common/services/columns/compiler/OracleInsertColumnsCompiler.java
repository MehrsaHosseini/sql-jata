package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.insert.common.services.columns.compiler;

import ir.mohaymen.querygenerator.domain.schema.column.Column;

import java.util.List;

public interface OracleInsertColumnsCompiler {

    String compile(List<Column> columnList);

}
