package com.tein.overcatchbackend.domain.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO extends BaseDto {

    private String next_made_up_to;
    private String next_due;
    private Object next_accounts;
    private String overdue;
    private Object last_accounts;
    private Object accounting_reference_date;



}
