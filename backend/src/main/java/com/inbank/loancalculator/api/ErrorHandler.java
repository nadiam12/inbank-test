package com.inbank.loancalculator.api;

import com.inbank.loancalculator.error.UserNotFoundException;
import com.inbank.loancalculator.error.ValidationException;
import com.inbank.loancalculator.dto.ErrorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@ControllerAdvice
public class ErrorHandler {

   private static final Logger logger = LogManager.getLogger(ErrorHandler.class);

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception) {
        logger.error(exception.getMessage(), exception);
        return buildErrorResponseEntity(new ErrorResponse(exception, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException exception) {
        logger.error(exception.getMessage(), exception);
        return buildErrorResponseEntity(new ErrorResponse(exception, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({RuntimeException.class, Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleAllUncaughtException(Exception exception) {
        logger.error(exception.getMessage(), exception);
        return buildErrorResponseEntity(new ErrorResponse("Error occurred, Please contact administrator", HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now(), exception.hashCode()));
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleRequestException(ServletRequestBindingException exception) {
        logger.error(exception.getMessage(), exception);
        return buildErrorResponseEntity(new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now(), exception.hashCode()));
    }

    private static ResponseEntity<ErrorResponse> buildErrorResponseEntity(ErrorResponse errorResponse) {
        return ResponseEntity
                .status(errorResponse.getStatus())
                .body(errorResponse);
    }
}
