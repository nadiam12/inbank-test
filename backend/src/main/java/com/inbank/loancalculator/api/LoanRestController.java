package com.inbank.loancalculator.api;

import com.inbank.loancalculator.dto.LoanResponse;
import com.inbank.loancalculator.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1/loan",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@AllArgsConstructor
public class LoanRestController {

    private final LoanService calculatorService;

    @GetMapping(path = "/calculate")
    public LoanResponse calculateLoan(
            @RequestParam long userId,
            @RequestParam int amount,
            @RequestParam int period) {
            return calculatorService.calculateLoan(userId, amount, period);
    }


}
