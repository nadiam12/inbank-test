package com.inbank.loancalculator.service;

import com.inbank.loancalculator.model.CreditModifier;
import com.inbank.loancalculator.model.Loan;
import com.inbank.loancalculator.model.LoanStatus;
import com.inbank.loancalculator.model.RejectionReason;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.inbank.loancalculator.service.LoanBySegmentsCalculatorService.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class LoanBySegmentsCalculatorServiceTest {

    private final LoanCalculatorService service = new LoanBySegmentsCalculatorService();

    @Test
    public void shouldReturnRejectedWithReasonDebtForDebtSegment() {
        Loan loan = service.calculateLoan(CreditModifier.DEBT, 0, 0);

        Assertions.assertEquals(LoanStatus.REJECTED, loan.getStatus());
        Assertions.assertEquals(RejectionReason.DEBT, loan.getRejectionReason());
    }

    @Test
    public void shouldConsiderMinPeriodIfRequestedPeriodBelowLimit() {
        Loan expectedLoan = service.calculateLoan(CreditModifier.SEGMENT_3, MIN_AMOUNT, MIN_PERIOD);
        Loan actualLoan = service.calculateLoan(CreditModifier.SEGMENT_3, MIN_AMOUNT, MIN_PERIOD / 2);

        assertEquals(expectedLoan.getPeriod(), actualLoan.getPeriod());
        assertEquals(expectedLoan.getAmount(), actualLoan.getAmount());
        verifyCreditScore(CreditModifier.SEGMENT_3, actualLoan.getAmount(), actualLoan.getPeriod());
    }

    @Test
    public void shouldConsiderMaxPeriodIfRequestedPeriodAboveLimit() {
        Loan expectedLoan = service.calculateLoan(CreditModifier.SEGMENT_3, MIN_AMOUNT, MAX_PERIOD);
        Loan actualLoan = service.calculateLoan(CreditModifier.SEGMENT_3, MIN_AMOUNT, MAX_PERIOD + 10);

        assertEquals(expectedLoan.getPeriod(), actualLoan.getPeriod());
        assertEquals(expectedLoan.getAmount(), actualLoan.getAmount());
        verifyCreditScore(CreditModifier.SEGMENT_3, actualLoan.getAmount(), actualLoan.getPeriod());
    }

    @Test
    public void shouldConsiderMaxAmountIfRequestedAmountAboveLimit() {
        Loan expectedLoan = service.calculateLoan(CreditModifier.SEGMENT_3, MAX_AMOUNT, MAX_PERIOD);
        Loan actualLoan = service.calculateLoan(CreditModifier.SEGMENT_3, MAX_AMOUNT * 2, MAX_PERIOD);

        assertEquals(expectedLoan.getPeriod(), actualLoan.getPeriod());
        assertEquals(expectedLoan.getAmount(), actualLoan.getAmount());
        verifyCreditScore(CreditModifier.SEGMENT_3, actualLoan.getAmount(), actualLoan.getPeriod());
    }

    @Test
    public void shouldConsiderMinAmountIfRequestedAmountBelowLimit() {
        Loan expectedLoan = service.calculateLoan(CreditModifier.SEGMENT_3, MIN_AMOUNT, MAX_PERIOD);
        Loan actualLoan = service.calculateLoan(CreditModifier.SEGMENT_3, MIN_AMOUNT / 2, MAX_PERIOD);

        assertEquals(expectedLoan.getPeriod(), actualLoan.getPeriod());
        assertEquals(expectedLoan.getAmount(), actualLoan.getAmount());
        verifyCreditScore(CreditModifier.SEGMENT_3, actualLoan.getAmount(), actualLoan.getPeriod());
    }

    @Test
    public void shouldReturnApprovedLoanWithRequestedAmountAndPeriod() {
        int requestedAmount = 3600;
        int requestedPeriod = MIN_PERIOD;

        Loan actualLoan = service.calculateLoan(CreditModifier.SEGMENT_2, requestedAmount, requestedPeriod);

        assertEquals(requestedPeriod, actualLoan.getPeriod());
        assertEquals(requestedAmount, actualLoan.getAmount());
        verifyCreditScore(CreditModifier.SEGMENT_2, actualLoan.getAmount(), actualLoan.getPeriod());
    }

    @Test
    public void shouldReturnGreaterAmountForRequestedPeriod() {
        int expectedAmount = 3600;
        int requestedAmount = MIN_AMOUNT;
        int requestedPeriod = MIN_PERIOD;

        Loan actualLoan = service.calculateLoan(CreditModifier.SEGMENT_2, requestedAmount, requestedPeriod);

        assertEquals(requestedPeriod, actualLoan.getPeriod());
        assertEquals(expectedAmount, actualLoan.getAmount());
        assertTrue(actualLoan.getAmount() > requestedAmount);
        verifyCreditScore(CreditModifier.SEGMENT_2, actualLoan.getAmount(), actualLoan.getPeriod());
    }

    @Test
    public void shouldReturnLowerAmountForRequestedPeriod() {
        int expectedAmount = 3600;
        int requestedAmount = MAX_AMOUNT;
        int requestedPeriod = MIN_PERIOD;

        Loan actualLoan = service.calculateLoan(CreditModifier.SEGMENT_2, requestedAmount, requestedPeriod);

        assertEquals(requestedPeriod, actualLoan.getPeriod());
        assertEquals(expectedAmount, actualLoan.getAmount());
        assertTrue(actualLoan.getAmount() < requestedAmount);
        verifyCreditScore(CreditModifier.SEGMENT_2, actualLoan.getAmount(), actualLoan.getPeriod());
    }

    @Test
    public void shouldReturnRequestedAmountForDifferentPeriod() {
        int expectedPeriod = 20;
        int requestedAmount = MIN_AMOUNT;
        int requestedPeriod = MIN_PERIOD;

        Loan actualLoan = service.calculateLoan(CreditModifier.SEGMENT_1, requestedAmount, requestedPeriod);

        assertEquals(expectedPeriod, actualLoan.getPeriod());
        assertEquals(requestedAmount, actualLoan.getAmount());
        assertTrue(actualLoan.getPeriod() > requestedPeriod);
        verifyCreditScore(CreditModifier.SEGMENT_1, actualLoan.getAmount(), actualLoan.getPeriod());
    }

    @Test
    public void shouldReturnRejectedLoanBecauseOfNoPeriodFound() {
        Loan actualLoan = service.calculateLoan(CreditModifier.SEGMENT_1, MAX_AMOUNT, MIN_PERIOD);

        assertEquals(LoanStatus.REJECTED, actualLoan.getStatus());
        assertEquals(RejectionReason.NO_PERIOD_FOUND, actualLoan.getRejectionReason());
    }

    private static void verifyCreditScore(CreditModifier creditModifier, Integer amount, Integer period) {
        assertTrue(calculateCreditScore(creditModifier, amount, period)>=1);
    }

}
