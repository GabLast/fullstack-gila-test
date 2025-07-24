package com.showcase.application.springbootbackend.exceptions;

public class ResourceExistsException extends RuntimeException {
    public ResourceExistsException(String message) {
        super(message);
    }
}
