package ir.mohaymen.querygenerator.domain.having;

/**
 * Filters the alias an aggregate was given in the select clause. Oracle cannot read a select alias
 * inside its own having clause, so such a condition is applied to the query as a whole instead.
 */
public record HavingCondition(String alias, String operation, Object value) implements Having {
}
