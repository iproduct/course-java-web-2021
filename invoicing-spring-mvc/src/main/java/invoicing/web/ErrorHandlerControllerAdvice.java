package invoicing.web;

import invoicing.dto.ErrorResponse;
import invoicing.exception.EntityNotFoundException;
import invoicing.exception.InvalidEntityDataException;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Order(1)
@ControllerAdvice (basePackageClasses = ProductController.class )
public class ErrorHandlerControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND).body(
                new ErrorResponse(NOT_FOUND.value(), e.getMessage())
        );
    }

    @ExceptionHandler
//    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleEntityConstraintViolations(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(BAD_REQUEST.value(), ex.getMessage(),
                ex.getBindingResult().getAllErrors().stream()
                        .map(err -> {
                            if (err instanceof FieldError) {
                                FieldError ferr = (FieldError) err;
                                String message = String.format("'%s': %s",
                                        ferr.getField(), ferr.getDefaultMessage());
                                if (ferr.getRejectedValue() != null && ferr.getRejectedValue().toString().length() > 0) {
                                    message += String.format(", invalid value: %s", ferr.getRejectedValue().toString());
                                }
                                return message;
                            } else {
                                return err.getDefaultMessage();
                            }
                        }).collect(Collectors.toList()))
        );
    }

    @ExceptionHandler
//    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleDbConstraintViolations(DataIntegrityViolationException ex) {
        Throwable cause = ex;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return ResponseEntity.badRequest().body(
                new ErrorResponse(BAD_REQUEST.value(), cause.getMessage())
        );
    }

    @ExceptionHandler
//    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleDbConstraintViolations(InvalidEntityDataException ex) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(BAD_REQUEST.value(), ex.getMessage())
        );
    }
}
