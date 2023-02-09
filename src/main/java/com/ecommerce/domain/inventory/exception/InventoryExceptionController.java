package com.ecommerce.domain.inventory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class InventoryExceptionController {

    @ExceptionHandler(value = ProductNotfoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotfoundException(ProductNotfoundException exception) {
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(
                new ErrorResponse(
                        200,
                        exception.getMessage()
                ),
                status
        );
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<List<FieldErrorResponse.FieldError>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        return new ResponseEntity<>(getFieldErrors(exception), HttpStatus.BAD_REQUEST);
    }

    private List<FieldErrorResponse.FieldError> getFieldErrors(final MethodArgumentNotValidException exception) {

        final List<FieldErrorResponse.FieldError> fieldErrors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(objectError -> {
            final String field = objectError.getField();
            final String error = objectError.getDefaultMessage();
            fieldErrors.add(new FieldErrorResponse.FieldError(field, error));
        });
        return fieldErrors;

    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldErrorResponse handleConstraintViolationException(final ConstraintViolationException exception) {

        // LOGGER.error("Bean validation failed with errors: {}", exception.getMessage());

        return new FieldErrorResponse(HttpStatus.BAD_REQUEST, "Invalid input",

                exception.getConstraintViolations()
                        .stream()
                        .map(e -> new FieldErrorResponse.FieldError(
                                e.getPropertyPath().toString(),
                                e.getMessage()
                        ))
                        .collect(Collectors.toList()));

    }


}
