package reviewme.global.exception;

public record FieldErrorResponse(
        String field,
        Object value,
        String message
){
}
