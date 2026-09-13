package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.services.window.resolver;

import ir.mohaymen.querygenerator.domain.limit_offset.Pagination;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.model.OraclePaginationWindow;

public interface OraclePaginationWindowResolver {

    OraclePaginationWindow resolve(Pagination pagination);

}
