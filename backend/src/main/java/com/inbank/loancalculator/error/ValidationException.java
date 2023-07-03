package com.inbank.loancalculator.error;


public class ValidationException extends RuntimeException {

    public static final String NEGATIVE_AMOUNT_MESSAGE = "The loan amount cannot be negative";
    public static final String NEGATIVE_PERIOD_MESSAGE = "The loan period cannot be negative";

    public ValidationException() {
        super();
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(String message) {
        super(message);
    }
}
