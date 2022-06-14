package com.adam.shop.exception;

public class ProductQuantityExceededException extends RuntimeException {
    public ProductQuantityExceededException(String message) {
        super(message);
    }
}
