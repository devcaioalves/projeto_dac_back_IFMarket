package io.github.devcaioalves.projetodacbackifmarket.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "ErrorMessage", description = "Standard error response structure")
public class ErrorResponse {

    @Schema(description = "Timestamp of the error", example = "2025-08-05T13:34:00")
    private final String timestamp = LocalDateTime.now().toString();

    @Schema(description = "Request path where the error occurred", example = "/events")
    private final String path;

    @Schema(description = "HTTP method used")
    private final String method;

    @Schema(description = "HTTP status code")
    private final int status;

    @Schema(description = "HTTP status text")
    private final String error;

    @Schema(description = "Error message")
    private final String message;

    @Schema(description = "Field-level validation errors errors (fields -> message)")
    private final Map<String, String> fieldErrors;

    // Constructor for generic errors
    public ErrorResponse(HttpServletRequest request, int status, String error, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status;
        this.error = error;
        this.message = message;
        this.fieldErrors = null;
    }

    // Constructor for validation errors
    public ErrorResponse(HttpServletRequest request, int status, String error, String message, BindingResult bindingResult) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status;
        this.error = error;
        this.message = message;
        this.fieldErrors = extractFieldErrors(bindingResult);
    }

    // Helper method to extract field errors from validation
    private Map<String, String> extractFieldErrors(BindingResult bindingResult) {
        if (!bindingResult.hasFieldErrors()) return null;

        Map<String, String> map = new LinkedHashMap<>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError f : fieldErrors) {
            map.put(f.getField(), f.getDefaultMessage());
        }
        return map.isEmpty() ? null : map;
    }
}