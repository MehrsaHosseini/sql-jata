package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.order_by.common.services.ordering.compiler;

import ir.mohaymen.querygenerator.domain.order.OrderBy;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.common.parameter.OracleParameterBinder;

public interface OracleOrderingCompiler {

    String compile(OrderBy orderBy, OracleParameterBinder parameterBinder);

}
