package com.inbank.loancalculator.service;

import com.inbank.loancalculator.model.CreditModifier;
import com.inbank.loancalculator.model.Loan;
import com.inbank.loancalculator.model.RejectionReason;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoanBySegmentsCalculatorService implements LoanCalculatorService {

    static final int MIN_AMOUNT = 2000;
    static final int MAX_AMOUNT = 10000;
    static final int MIN_PERIOD = 12;
    static final int MAX_PERIOD = 60;


    @Override
    public Loan calculateLoan(CreditModifier creditModifier, Integer amount, Integer period) {
        if (creditModifier == CreditModifier.DEBT) {
            return Loan.createRejectedLoan(RejectionReason.DEBT);
        }

        period = inlinePeriod(period);
        amount = inlineAmount(amount);

        Optional<Integer> approvedLoanAmount = calculateMaxApprovalAmount(creditModifier, period);
        if (approvedLoanAmount.isEmpty()) {
            Optional<Integer> periodOptional = findSuitablePeriod(creditModifier, amount);
            if (periodOptional.isEmpty()) {
                return  Loan.createRejectedLoan(RejectionReason.NO_PERIOD_FOUND);
            }
            period = periodOptional.get();
            approvedLoanAmount = calculateMaxApprovalAmount(creditModifier, period);

            if (approvedLoanAmount.isEmpty()) {
                return Loan.createRejectedLoan(RejectionReason.NO_AMOUNT_FOUNT);
            }
        }

        return Loan.createApprovedLoan(approvedLoanAmount.get(), period);
    }

    public static double calculateCreditScore(CreditModifier creditModifier, Integer amount, Integer period) {
        return ((double) creditModifier.getModifier()/amount)*period;
    }

    private int inlineAmount(Integer amount) {
        if (amount < MIN_AMOUNT) return MIN_AMOUNT;
        if (amount > MAX_AMOUNT) return MAX_AMOUNT;
        return amount;
    }

    private int inlinePeriod(Integer period) {
        if (period < MIN_PERIOD) return MIN_PERIOD;
        if (period > MAX_PERIOD) return MAX_PERIOD;
        return period;
    }

    private Optional<Integer> calculateMaxApprovalAmount(CreditModifier creditModifier, int loanPeriod) {
        int loanAmount = creditModifier.getModifier() * loanPeriod;
        if (loanAmount < MIN_AMOUNT) return Optional.empty();
        if (loanAmount > MAX_AMOUNT) loanAmount = MAX_AMOUNT;
        return Optional.of(loanAmount);
    }

    private Optional<Integer> findSuitablePeriod(CreditModifier creditModifier, int loanAmount) {
        int period = loanAmount / creditModifier.getModifier();
        if (period < MIN_PERIOD) return Optional.of(MIN_PERIOD);
        if (period > MAX_PERIOD) return Optional.empty();
        return Optional.of(period);
    }
}
