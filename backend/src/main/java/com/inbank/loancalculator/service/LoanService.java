package com.inbank.loancalculator.service;

import com.inbank.loancalculator.dto.LoanResponse;

public interface LoanService {

    LoanResponse calculateLoan(long userId, int amount, int periodMonth);
}
