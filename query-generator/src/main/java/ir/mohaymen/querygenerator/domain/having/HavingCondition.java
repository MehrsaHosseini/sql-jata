package ir.mohaymen.querygenerator.domain.having;


public record HavingCondition(String alias, String operation, Object value) implements Having {
}
