package ir.mohaymen.querygenerator.domain.limit_offset;

public record Limit_offset(Integer offset, Integer limit) implements Pagination {
}
