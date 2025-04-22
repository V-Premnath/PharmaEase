package com.pharmaease.backend.exception;

public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 5173748193588347094L;

	public UserNotFoundException(String message) {
        super(message);
    }
}
