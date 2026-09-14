package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.services.page.resolver;

import ir.mohaymen.querygenerator.domain.limit_offset.Page_size;
import ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.model.OraclePaginationWindow;


public class OraclePageSizeWindowResolverImpl implements OraclePageSizeWindowResolver {

    private static final int FIRST_PAGE = 0;
    private static final int MINIMUM_PAGE_SIZE = 1;

    @Override
    public OraclePaginationWindow resolve(Page_size pageSize) {
        if (pageSize == null) {
            throw new IllegalArgumentException("page size pagination must not be null");
        }

        int page = resolvePage(pageSize.page());
        Integer size = pageSize.pageSize();
        if (size == null) {
            if (page != FIRST_PAGE) {
                throw new IllegalArgumentException("page size is required to skip to page " + page);
            }
            return OraclePaginationWindow.empty();
        }
        if (size < MINIMUM_PAGE_SIZE) {
            throw new IllegalArgumentException("page size must be at least " + MINIMUM_PAGE_SIZE + " but was: " + size);
        }

        long offset = (long) (page - FIRST_PAGE) * size;
        return new OraclePaginationWindow(offset, size.longValue());
    }

    private int resolvePage(Integer page) {
        if (page == null) {
            return FIRST_PAGE;
        }
        if (page < FIRST_PAGE) {
            throw new IllegalArgumentException("page number must not be below " + FIRST_PAGE + " but was: " + page);
        }
        return page;
    }

}
