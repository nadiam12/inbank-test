package com.inbank.loancalculator.error;

public class UserNotFoundException extends RuntimeException{

    public static final String USER_NOT_FOUND_MESSAGE = "User not found. Please, check personal code.";

    public UserNotFoundException() {
        super(USER_NOT_FOUND_MESSAGE);
    }
    public UserNotFoundException(Throwable cause) {
        super(USER_NOT_FOUND_MESSAGE, cause);
    }
}
