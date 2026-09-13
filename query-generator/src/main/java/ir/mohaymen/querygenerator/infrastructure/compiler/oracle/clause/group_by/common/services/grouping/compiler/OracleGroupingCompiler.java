package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.group_by.common.services.grouping.compiler;

import ir.mohaymen.querygenerator.domain.group.GroupBy;

public interface OracleGroupingCompiler {

    String compile(GroupBy groupBy);

}
