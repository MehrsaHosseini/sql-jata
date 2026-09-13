package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.services.offset.resolver;

import ir.mohaymen.querygenerator.domain.limit_offset.Limit_offset;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.model.OraclePaginationWindow;

public interface OracleLimitOffsetWindowResolver {

    OraclePaginationWindow resolve(Limit_offset limitOffset);

}
