package com.tein.overcatchbackend.domain.model;

import com.tein.overcatchbackend.domain.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "BANK")
@EqualsAndHashCode(callSuper = true)
public class Bank extends BaseEntity {

    @Column(name = "ACCOUNT_IBAN")
    private String accountIBAN;

    @Column(name = "BANK_ACCOUNT_NUMBER")
    private Integer accountNumber;

    @Column(name = "BANK_NAME")
    private String bankName;

    @Column(name = "BANK_SORT_CODE")
    private String sortCode;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID",referencedColumnName = "id")
    private Client client;

}
