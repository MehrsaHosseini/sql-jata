package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.services.offset.resolver;

import ir.mohaymen.querygenerator.domain.limit_offset.Limit_offset;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.model.OraclePaginationWindow;

public class OracleLimitOffsetWindowResolverImpl implements OracleLimitOffsetWindowResolver {

    private static final int MINIMUM_OFFSET = 0;
    private static final int MINIMUM_LIMIT = 1;

    @Override
    public OraclePaginationWindow resolve(Limit_offset limitOffset) {
        if (limitOffset == null) {
            throw new IllegalArgumentException("limit offset pagination must not be null");
        }

        return new OraclePaginationWindow(resolveOffset(limitOffset.offset()), resolveLimit(limitOffset.limit()));
    }

    private Long resolveOffset(Integer offset) {
        if (offset == null) {
            return null;
        }
        if (offset < MINIMUM_OFFSET) {
            throw new IllegalArgumentException("pagination offset must not be below " + MINIMUM_OFFSET + " but was: " + offset);
        }
        return offset.longValue();
    }

    private Long resolveLimit(Integer limit) {
        if (limit == null) {
            return null;
        }
        if (limit < MINIMUM_LIMIT) {
            throw new IllegalArgumentException("pagination limit must be at least " + MINIMUM_LIMIT + " but was: " + limit);
        }
        return limit.longValue();
    }

}
