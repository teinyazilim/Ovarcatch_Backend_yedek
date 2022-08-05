package com.tein.overcatchbackend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankStatement {
    private String moneyIn;
    private String moneyOut;
    private String balance;
    private String statementDate;
    private String statementDescription;
}
