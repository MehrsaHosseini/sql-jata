package ir.mohaymen.querygenerator.api.common.dto;

public record JoinOnRequest(String left, String operation, String right) {

    public JoinOnRequest(String left, String right) {
        this(left, "=", right);
    }
}
