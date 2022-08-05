package com.tein.overcatchbackend.domain.model;

import com.tein.overcatchbackend.domain.BaseEntity;
import com.tein.overcatchbackend.enums.PersonelExpenditure;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "BUYER_DETAIL")
@EqualsAndHashCode(callSuper = true)
public class Buyer extends BaseEntity {

    @Column(name = "BUYER_NAME")
    private String buyerName;

    @Column(name = "COMMERCIAL_TITLE")
    private String commercialTitle;

    @Column(name = "BUYER_ADDRESS")
    private String buyerAddress;

    @Column(name = "BUYER_EMAIL")
    private String buyerEmail;

    @Column(name = "BUYER_PHONE")
    private String buyerPhone;

    @Column(name = "BUYER_FAX")
    private String buyerFax;

    @Column(name = "BUYER_IBAN")
    private String buyerIBAN;

    @Column(name = "BUYER_ACCOUNT")
    private String buyerAccount;

    @Column(name = "BUYER_BANK")
    private String buyerBank;

    @Column(name = "BUYER_BRANCH_BANK")
    private String buyerBranchBank;

    @Column(name = "ACCOUNT_TYPE")
    private String accountType;

    @Column(name = "VAT_NUMBER")
    private Long vatNumber;

    @Column(name = "COMPANY_REGISTER")
    private Long companyRegister;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID",referencedColumnName = "id")
    private Client client;

}