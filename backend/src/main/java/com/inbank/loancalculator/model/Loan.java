package com.inbank.loancalculator.model;

import lombok.Getter;

@Getter
public class Loan {

    private final LoanStatus status;
    private Integer amount;
    private Integer period;
    private RejectionReason rejectionReason;

    private Loan(Integer amount, Integer period) {
        this.amount = amount;
        this.period = period;
        this.status = LoanStatus.APPROVED;
    }

    private Loan(RejectionReason reason) {
        this.status = LoanStatus.REJECTED;
        this.rejectionReason = reason;
    }

    public static Loan createApprovedLoan(Integer amount, Integer period) {
        return new Loan(amount, period);
    }

    public static Loan createRejectedLoan(RejectionReason reason) {
        return new Loan(reason);
    }
}
