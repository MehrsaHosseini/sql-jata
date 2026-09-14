package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.order_by.common.services.item.compiler;

import ir.mohaymen.querygenerator.domain.order.OrderByItem;

public interface OracleOrderByItemCompiler {

    String compile(OrderByItem orderByItem);

}
