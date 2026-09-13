package ir.mohaymen.querygenerator.domain.having;

/**
 * Filters an aggregate expression such as {@code COUNT(*) > 50} in place, written by the caller.
 */
public record HavingRaw(String raw) implements Having {
}
