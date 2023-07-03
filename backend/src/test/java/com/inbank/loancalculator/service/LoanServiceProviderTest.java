package com.inbank.loancalculator.service;

import com.inbank.loancalculator.error.ValidationException;
import com.inbank.loancalculator.model.*;
import com.inbank.loancalculator.dto.LoanResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoanServiceProviderTest {

    @Mock
    private UserService userService;
    @Mock
    private LoanCalculatorService calculatorService;
    @InjectMocks
    private LoanServiceProvider loanService;

    @Test
    public void shouldThrowExceptionForNegativeAmount() {
        Assertions.assertThrows(
                ValidationException.class,
                () -> loanService.calculateLoan(1, -100, 10),
                ValidationException.NEGATIVE_AMOUNT_MESSAGE
        );
    }

    @Test
    public void shouldThrowExceptionForNegativePeriod() {
        Assertions.assertThrows(
                ValidationException.class,
                () -> loanService.calculateLoan(1, 100, -10),
                ValidationException.NEGATIVE_PERIOD_MESSAGE
        );
    }

    @Test
    public void shouldReturnRejectionResponseForRejectedLoan() {
        when(userService.getUser(0)).thenReturn(new User(0, CreditModifier.DEBT));
        when(calculatorService.calculateLoan(CreditModifier.DEBT, 2000, 12)).thenReturn(
                Loan.createRejectedLoan(RejectionReason.DEBT)
        );

        LoanResponse loanResponse = loanService.calculateLoan(0, 2000, 12);

        assertEquals(LoanStatus.REJECTED, loanResponse.getStatus());
    }

    @Test
    public void shouldReturnApprovedResponse() {
        when(userService.getUser(0)).thenReturn(new User(0, CreditModifier.SEGMENT_1));
        when(calculatorService.calculateLoan(CreditModifier.SEGMENT_1, 2000, 12)).thenReturn(
                Loan.createApprovedLoan(2000, 12)
        );
        LoanResponse loanResponse = loanService.calculateLoan(0, 2000, 12);

        assertEquals(LoanStatus.APPROVED, loanResponse.getStatus());
    }
}
