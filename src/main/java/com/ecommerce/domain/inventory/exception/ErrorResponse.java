package com.ecommerce.domain.inventory.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private int error_code;
    private String error_details;

    public ErrorResponse(String message)
    {
        super();
        this.error_details = message;
    }
}
