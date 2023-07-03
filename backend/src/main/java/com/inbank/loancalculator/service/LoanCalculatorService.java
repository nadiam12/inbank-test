package com.inbank.loancalculator.service;

import com.inbank.loancalculator.model.CreditModifier;
import com.inbank.loancalculator.model.Loan;

public interface LoanCalculatorService {

    Loan calculateLoan(CreditModifier creditModifier, Integer amount, Integer period);
}
