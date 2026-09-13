package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.order_by.common.services.ordering.compiler;

import ir.mohaymen.querygenerator.domain.order.OrderBy;

public interface OracleOrderingCompiler {

    String compile(OrderBy orderBy);

}
