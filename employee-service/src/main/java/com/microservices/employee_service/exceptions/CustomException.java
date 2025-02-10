package com.microservices.employee_service.exceptions;

public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }
}
