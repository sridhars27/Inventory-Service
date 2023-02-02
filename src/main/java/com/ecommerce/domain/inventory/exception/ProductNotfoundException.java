package com.ecommerce.domain.inventory.exception;

public class ProductNotfoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String message;

    public ProductNotfoundException() {
    }

    public ProductNotfoundException(String msg) {
        super(msg);
        this.message = msg;
    }

}
