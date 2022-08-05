package com.tein.overcatchbackend.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.tein.overcatchbackend.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "COMPANY")
@EqualsAndHashCode(callSuper = true)
public class Company extends BaseEntity {

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "GATEWAY_ID")
    private String gateway;

    @ManyToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinTable(name = "DIRECTOR_COMPANY", joinColumns = {
            @JoinColumn(name = "CLIENT_ID", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "DIRECTOR_DETAIL_ID", referencedColumnName = "id")})
    private Set<DirectorDetail> directorDetails = new HashSet<>();

    @Column(name = "DUE_DATE")
    private LocalDate dueDate;

    @Column(name = "REGISTRATION")
    private String registration;

    @Column(name = "AUTHENTICATION")
    private String authentication;

    @Column(name = "PAYE_NUMBER")
    private String payeNumber;

    @Column(name = "PHONE")
    private String phoneNumber;

    @Column(name = "EORI_NUMBER")
    private String eoriNumber;

    @Column(name = "COMPANY_NUMBER")
    private String companyNumber;

    @Column(name = "VAT_NUMBER")
    private String vatNumber;

    @Column(name = "VAT_PERIOD")
    private String vatPeriod;

    @Column(name = "VAT_REGISTER_DATE")
    private String vatRegisterDate;

    @Column(name = "TAX_RETURN")
    private String taxReturn;

    @Column(name = "COMPANY_UTR")
    private String companyUtr;

    @Column(name = "NATIONALITY")
    private String nationality;

    @Column(name = "PAYE_OFFICE_NUMBER")
    private String paOfficeNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "INCORPORATED_DATE")
    private LocalDate incorporatedDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "NEXT_STATEMENT_DATE")
    private LocalDate nextStatementDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "STATEMENT_DUE_DATE")
    private LocalDate statementDueDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "LAST_STATEMENT_DATE")
    private LocalDate lastStatementDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "NEXT_ACCOUNTS_DATE")
    private LocalDate nextAccountsDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "ACCOUNTS_DUE_DATE")
    private LocalDate accountsDueDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "LAST_ACCOUNTS_DATE")
    private LocalDate lastAccountsDate;

    @OneToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client clientInfo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "NATURE_BUSINESS_COMPANY", joinColumns = {
            @JoinColumn(name = "INCORPRATION_ID", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "NATURE_BUSINESS_ID", referencedColumnName = "id")})
    private Set<NatureBusiness> natureBusinesses;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "YEARENDDATE")
    private LocalDate yearEndDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "QUARTEREND")
    private LocalDate quarterEnd;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "CONFIRMATIONSTATEMENT")
    private LocalDate confirmationStatement;
}
