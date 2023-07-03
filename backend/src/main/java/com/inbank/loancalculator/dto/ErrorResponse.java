package com.inbank.loancalculator.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@JsonFormat
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {

    private final HttpStatus status;
    private final String message;
    private final String dateTime;
    private final int hashCode;


    public ErrorResponse(Exception exception, HttpStatus status) {
        this.status = status;
        this.message = exception.getMessage();
        this.dateTime = LocalDateTime.now().toString();
        this.hashCode = exception.hashCode();
    }

    public ErrorResponse(String message, HttpStatus status, LocalDateTime dateTime, int hashCode) {
        this.status = status;
        this.message = message;
        this.dateTime = dateTime.toString();
        this.hashCode = hashCode;
    }

}
