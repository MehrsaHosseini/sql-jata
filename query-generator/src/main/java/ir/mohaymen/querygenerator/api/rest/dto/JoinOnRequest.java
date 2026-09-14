package ir.mohaymen.querygenerator.api.rest.dto;

public record JoinOnRequest(String left, String operation, String right) {

    public JoinOnRequest(String left, String right) {
        this(left, "=", right);
    }
}
