package reviewme.global;

import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.server.MissingRequestValueException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import reviewme.global.exception.BadRequestException;
import reviewme.global.exception.FieldErrorResponse;
import reviewme.global.exception.NotFoundException;
import reviewme.global.exception.ReviewMeException;
import reviewme.global.exception.UnAuthorizedException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException ex) {
        logReviewMeException(ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getErrorMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail handleBadRequestException(BadRequestException ex) {
        logReviewMeException(ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getErrorMessage());
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ProblemDetail handleUnAuthorizedException(UnAuthorizedException ex) {
        logReviewMeException(ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getErrorMessage());
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception ex) {
        logInitialServerError(ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다.");
    }

    // Following exceptions are exceptions that occur in Spring
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ProblemDetail handleHttpRequestMethodNotSupportedException(Exception ex) {
        logSpringException(ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메서드입니다.");
    }
    @ExceptionHandler(HttpMediaTypeException.class)
    public ProblemDetail handleHttpMediaTypeException(Exception ex) {
        logSpringException(ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "잘못된 media type 입니다.");
    }

    @ExceptionHandler({MissingRequestValueException.class, MissingServletRequestPartException.class})
    public ProblemDetail handleMissingRequestException(Exception ex) {
        logSpringException(ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "필수 요청 데이터가 누락되었습니다.");
    }

    @ExceptionHandler({ServletRequestBindingException.class, HttpMessageNotReadableException.class})
    public ProblemDetail handleServletRequestBindingException(Exception ex) {
        logSpringException(ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "요청을 읽을 수 없습니다.");
    }

    @ExceptionHandler({
            MethodValidationException.class, BindException.class,
            TypeMismatchException.class, HandlerMethodValidationException.class
    })
    public ProblemDetail handleRequestFormatException(Exception ex) {
        logSpringException(ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "요청의 형식이 잘못되었습니다.");
    }

    @ExceptionHandler({NoHandlerFoundException.class, NoResourceFoundException.class})
    public ProblemDetail handleNoHandlerFoundException(Exception ex) {
        logSpringException(ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "잘못된 경로의 요청입니다.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        logSpringException(ex);
        List<FieldErrorResponse> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new FieldErrorResponse(
                        fieldError.getField(),
                        fieldError.getRejectedValue(),
                        fieldError.getDefaultMessage()))
                .toList();

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "요청 형식이 올바르지 않습니다."
        );
        Map<String, Object> properties = Map.of("fieldErrors", fieldErrors);
        problemDetail.setProperties(properties);
        return problemDetail;
    }

    private void logReviewMeException(ReviewMeException ex) {
        log.info("{} is occurred - {}",
                ex.getClass().getSuperclass().getSimpleName(),
                ex.getClass().getSimpleName(),
                ex
        );
    }

    private void logInitialServerError(Exception ex) {
        log.error("Initial server error is occurred", ex);
    }

    private void logSpringException(Exception ex) {
        log.info("Spring error is occurred - {}: {}", ex.getClass().getSimpleName(), ex.getLocalizedMessage());
    }
}
