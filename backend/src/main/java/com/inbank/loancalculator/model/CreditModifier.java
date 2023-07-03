package com.inbank.loancalculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CreditModifier {

    DEBT(0),
    SEGMENT_1( 100),
    SEGMENT_2(300),
    SEGMENT_3(1000);

    @Getter
    private final int modifier;
}
