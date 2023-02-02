package com.ecommerce.domain.inventory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

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
}
