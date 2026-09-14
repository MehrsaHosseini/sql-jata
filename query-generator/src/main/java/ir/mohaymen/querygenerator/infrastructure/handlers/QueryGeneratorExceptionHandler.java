package ir.mohaymen.querygenerator.infrastructure.handlers;

import ir.mohaymen.querygenerator.api.rest.common.dto.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class QueryGeneratorExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ApiError> handleBadRequest(RuntimeException exception) {
        return ResponseEntity.badRequest().body(new ApiError("bad_request", exception.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleUnreadable(HttpMessageNotReadableException exception) {
        String message = exception.getMostSpecificCause() == null
                ? "request body is not valid JSON"
                : exception.getMostSpecificCause().getMessage();
        return ResponseEntity.badRequest().body(new ApiError("bad_request", message));
    }
}
