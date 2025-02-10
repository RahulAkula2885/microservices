package com.microservices.address_service.exceptions;

public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }
}
