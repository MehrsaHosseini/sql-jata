package ir.mohaymen.querygenerator.infrastructure.compiler.oracle.clause.pagination.common.model;

public record OraclePaginationWindow(Long offset, Long limit) {

    private static final OraclePaginationWindow EMPTY = new OraclePaginationWindow(null, null);

    private static final long MINIMUM_OFFSET = 0;
    private static final long MINIMUM_LIMIT = 1;

    public OraclePaginationWindow {
        if (offset != null && offset < MINIMUM_OFFSET) {
            throw new IllegalArgumentException("pagination offset must not be below " + MINIMUM_OFFSET + " but was: " + offset);
        }
        if (limit != null && limit < MINIMUM_LIMIT) {
            throw new IllegalArgumentException("pagination limit must be at least " + MINIMUM_LIMIT + " but was: " + limit);
        }
        if (offset != null && offset == MINIMUM_OFFSET) {
            offset = null;
        }
    }

    public static OraclePaginationWindow empty() {
        return EMPTY;
    }

    public boolean hasOffset() {
        return offset != null;
    }

    public boolean hasLimit() {
        return limit != null;
    }

    public boolean isEmpty() {
        return !hasOffset() && !hasLimit();
    }

}
