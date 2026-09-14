package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.services.window.resolver;

import ir.mohaymen.querygenerator.domain.limit_offset.Limit_offset;
import ir.mohaymen.querygenerator.domain.limit_offset.Page_size;
import ir.mohaymen.querygenerator.domain.limit_offset.Pagination;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.model.OraclePaginationWindow;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.services.offset.resolver.OracleLimitOffsetWindowResolver;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.services.offset.resolver.OracleLimitOffsetWindowResolverImpl;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.services.page.resolver.OraclePageSizeWindowResolver;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.services.page.resolver.OraclePageSizeWindowResolverImpl;

import java.util.Objects;

public class OraclePaginationWindowResolverImpl implements OraclePaginationWindowResolver {

    private final OracleLimitOffsetWindowResolver limitOffsetWindowResolver;
    private final OraclePageSizeWindowResolver pageSizeWindowResolver;

    public OraclePaginationWindowResolverImpl() {
        this(new OracleLimitOffsetWindowResolverImpl(), new OraclePageSizeWindowResolverImpl());
    }

    public OraclePaginationWindowResolverImpl(OracleLimitOffsetWindowResolver limitOffsetWindowResolver,
                                              OraclePageSizeWindowResolver pageSizeWindowResolver) {
        this.limitOffsetWindowResolver = Objects.requireNonNull(limitOffsetWindowResolver, "limit offset window resolver must not be null");
        this.pageSizeWindowResolver = Objects.requireNonNull(pageSizeWindowResolver, "page size window resolver must not be null");
    }

    @Override
    public OraclePaginationWindow resolve(Pagination pagination) {
        return switch (pagination) {
            case null -> OraclePaginationWindow.empty();
            case Limit_offset limitOffset -> limitOffsetWindowResolver.resolve(limitOffset);
            case Page_size pageSize -> pageSizeWindowResolver.resolve(pageSize);
        };
    }

}
