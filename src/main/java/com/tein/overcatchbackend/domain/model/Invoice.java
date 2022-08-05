package com.tein.overcatchbackend.domain.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.tein.overcatchbackend.domain.BaseEntity;
import com.tein.overcatchbackend.enums.InvoiceType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "INVOICE")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Invoice extends BaseEntity {

    @Column(name = "INVOICE_NUMBER")
    private String invoiceCode;

    @Column(name = "INVOICE_TYPE")
    private InvoiceType invoiceType;

    @Column(name = "CLIENT_ADDRESS")
    private String clientAddress;

    @Column(name = "COMPANY_NUMBER")
    private String companyNumber;

    @Column(name = "CLIENT_EMAIL")
    private String clientEmail;

    @Column(name = "CLIENT_PHONE")
    private String clientPhone;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "INVOICE_DATE")
    private LocalDate invoiceDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "INVOICE_DUE_DATE")
    private LocalDate dueDate;

    @Column(name = "VAT")
    private Float vat;

    @Column(name = "CURRENCY_CODE")
    private String currencyOfPayment;

    @Column(name = "TOTAL")
    private Float total;

    @Column(name = "SUB_TOTAL")
    private Float subTotal;

    @Column(name = "BUYER_NAME")
    private String buyerName;

    @Column(name = "COMMERCIAL_TITLE")
    private String commercialTitle;

    @Column(name = "BUYER_ADDRESS")
    private String buyerAddress;

    @Column(name = "BUYER_PHONE")
    private String buyerPhone;

    @Column(name = "BUYER_EMAIL")
    private String buyerEmail;

    @Column(name = "UPDATE_STATE")
    private Integer updateState;

    @Column(name = "DELETE_STATE")
    private Integer deleteState;

    @Column(name = "WEB")
    private String web;

    @Column(name = "SELECTED_INVOICE_TYPE")
    private Integer selectedInvoiceType;

    @ManyToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinTable(name = "INVOICE_TASKS", joinColumns = {
            @JoinColumn(name = "INVOICE_ID", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "TASK_ID", referencedColumnName = "id")})
    private Set<Tasks> tasks = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID",referencedColumnName = "id")
//    @JsonIgnore
    private Client client;

    @OneToMany(mappedBy = "invoice",cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
//    @JsonIgnore
    private List<InvoiceDetail> invoiceDetails;

    @ManyToOne
    @JoinColumn(name = "BANK_ID",referencedColumnName = "id")
//    @JsonIgnore
    private Bank bank;
}
