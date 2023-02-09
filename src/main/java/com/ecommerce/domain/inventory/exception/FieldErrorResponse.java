package com.ecommerce.domain.inventory.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

public class FieldErrorResponse extends ErrorResponse {


    @JsonProperty
    @Getter
    private final List<FieldError> errors;

    public FieldErrorResponse(final HttpStatus status, final String message, final List<FieldError> errors) {
        super(message);
        this.errors = errors;

    }


    @AllArgsConstructor
    @Getter
    @Setter
    public static class FieldError {

        @JsonProperty
        private final String field;


        @JsonProperty
        private final String error;
    }

}
