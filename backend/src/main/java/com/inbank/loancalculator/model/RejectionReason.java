package com.inbank.loancalculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum RejectionReason {

    DEBT("The user has a debt. Cannot give a loan"),
    NO_AMOUNT_FOUNT("Cannot find appropriate loan amount"),
    NO_PERIOD_FOUND("Cannot find suitable period for the loan");

    @Getter
    private final String message;

    @Override
    public String toString() {
        return message;
    }
}
