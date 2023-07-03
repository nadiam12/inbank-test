package com.inbank.loancalculator.service;

import com.inbank.loancalculator.dto.LoanResponse;
import com.inbank.loancalculator.error.ValidationException;
import com.inbank.loancalculator.model.Loan;
import com.inbank.loancalculator.model.LoanStatus;
import com.inbank.loancalculator.model.RejectionReason;
import com.inbank.loancalculator.model.User;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoanServiceProvider implements LoanService {

    private static final Logger logger = LogManager.getLogger(LoanServiceProvider.class);

    @Autowired
    private final UserService userService;
    @Autowired
    private final LoanCalculatorService calculatorService;

    @Override
    public LoanResponse calculateLoan(long userId, int amount, int period) {
        validateAmount(amount);
        validatePeriod(period);

        User user = userService.getUser(userId);

        Loan loan = calculatorService.calculateLoan(user.getCreditModifier(), amount, period);
        if (loan.getStatus() == LoanStatus.REJECTED) {
            logger.info(String.format("Loan was rejected for userId=%d: creditModifier=%s, amount=%d, period=%d with the reason=%s",
                    userId, user.getCreditModifier(), amount, period, loan.getRejectionReason()));
            return buildRejectionResponse(loan.getRejectionReason());
        }
        logger.info(String.format("Loan was approved for userId=%d: amount=%d, period=%d", userId, amount, period));
        return new LoanResponse.LoanResponseBuilder()
                .status(LoanStatus.APPROVED)
                .amount(loan.getAmount())
                .period(loan.getPeriod())
                .build();
    }

    private void validatePeriod(int period) {
        if (period < 0) {
            logger.error("The requested period: " + period + " is negative");
            throw new ValidationException(ValidationException.NEGATIVE_PERIOD_MESSAGE);
        }
    }

    private void validateAmount(int amount) {
        if (amount < 0) {
            logger.error("The requested amount: " + amount + " is negative");
            throw new ValidationException(ValidationException.NEGATIVE_AMOUNT_MESSAGE);
        }
    }

    private static LoanResponse buildRejectionResponse(RejectionReason reason) {
        return new LoanResponse.LoanResponseBuilder().status(LoanStatus.REJECTED).reason(reason).build();
    }
}
