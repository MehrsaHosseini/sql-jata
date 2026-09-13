package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.services.page.resolver;

import ir.mohaymen.querygenerator.domain.limit_offset.Page_size;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.model.OraclePaginationWindow;

public interface OraclePageSizeWindowResolver {

    OraclePaginationWindow resolve(Page_size pageSize);

}
