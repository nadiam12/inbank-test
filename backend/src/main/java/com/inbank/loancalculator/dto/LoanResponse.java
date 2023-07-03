package com.inbank.loancalculator.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inbank.loancalculator.model.LoanStatus;
import com.inbank.loancalculator.model.RejectionReason;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoanResponse {

    private final LoanStatus status;
    private final RejectionReason reason;
    private final Integer amount;
    private final Integer period;

    private LoanResponse(LoanResponseBuilder builder) {
        this.status = builder.status;
        this.reason = builder.reason;
        this.amount = builder.amount;
        this.period = builder.period;
    }

    public static class LoanResponseBuilder {
        private LoanStatus status;
        private RejectionReason reason;
        private Integer amount;
        private Integer period;

        public LoanResponseBuilder status(LoanStatus status) {
            this.status = status;
            return this;
        }

        public LoanResponseBuilder reason(RejectionReason reason) {
            this.reason = reason;
            return this;
        }

        public LoanResponseBuilder amount(int amount) {
            this.amount = amount;
            return this;
        }

        public LoanResponseBuilder period(int period) {
            this.period = period;
            return this;
        }

        public LoanResponse build() {
            return new LoanResponse(this);
        }
    }
}
