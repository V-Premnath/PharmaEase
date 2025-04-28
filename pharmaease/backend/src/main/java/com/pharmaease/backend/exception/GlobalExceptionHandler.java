package com.pharmaease.backend.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Handle 404 Not Found (No Handler Found)
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            org.springframework.web.servlet.NoHandlerFoundException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        return new ResponseEntity<>(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.NOT_FOUND.value(),
                "error", "Not Found",
                "message", "The requested endpoint was not found.",
                "path", request.getDescription(false).replace("uri=", "")
        ), HttpStatus.NOT_FOUND);
    }

    // Handle All Other Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception ex, WebRequest request) {
    	logger.error("Unhandled exception occurred: ", ex);

        // Get the first element of the stack trace (where the exception was thrown)
        StackTraceElement element = ex.getStackTrace()[0];

        String fileName = element.getFileName(); // e.g., AuthController.java
        String methodName = element.getMethodName(); // e.g., redirectToGoogle
        int lineNumber = element.getLineNumber(); // e.g., 45

        logger.error("Exception thrown in file: {"+ fileName+"}, "+ "method: {"+methodName+"at line: {"+ lineNumber+"}");

        return new ResponseEntity<>(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "error", "Internal Server Error",
                "message", ex.getMessage(),
                "path", request.getDescription(false).replace("uri=", "")
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }
//
//    // (Optional) Add more specific handlers as needed
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<?> handleBadRequest(Exception ex, WebRequest request) {
//        return new ResponseEntity<>(Map.of(
//
//                "message", ex.getMessage(),
//                "timestamp", LocalDateTime.now(),
//                "status", HttpStatus.BAD_REQUEST.value(),
//                "error", "Bad Request",
//                "path", request.getDescription(false).replace("uri=", "")
//        ), HttpStatus.BAD_REQUEST);
//    }
// // Add inside GlobalExceptionHandler.java
//
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex, WebRequest request) {
//        return new ResponseEntity<>(Map.of(
//                "timestamp", LocalDateTime.now(),
//                "status", HttpStatus.NOT_FOUND.value(),
//                "error", "User Not Found",
//                "message", ex.getMessage(),
//                "path", request.getDescription(false).replace("uri=", "")
//        ), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(InvalidCredentialsException.class)
//    public ResponseEntity<?> handleInvalidCredentials(InvalidCredentialsException ex, WebRequest request) {
//        return new ResponseEntity<>(Map.of(
//                "timestamp", LocalDateTime.now(),
//                "status", HttpStatus.UNAUTHORIZED.value(),
//                "error", "Invalid Credentials",
//                "message", ex.getMessage(),
//                "path", request.getDescription(false).replace("uri=", "")
//        ), HttpStatus.UNAUTHORIZED);
//    }

}
