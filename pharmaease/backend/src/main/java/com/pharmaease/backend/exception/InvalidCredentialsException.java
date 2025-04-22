package com.pharmaease.backend.exception;

public class InvalidCredentialsException extends RuntimeException {
    private static final long serialVersionUID = -171593581323290385L;

	public InvalidCredentialsException(String message) {
        super(message);
    }
}
