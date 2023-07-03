package com.inbank.loancalculator.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name="id")
    private long userId;

    @Column(name = "credit_modifier")
    @Enumerated(EnumType.STRING)
    private CreditModifier creditModifier;
}
