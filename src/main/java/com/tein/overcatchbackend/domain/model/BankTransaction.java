package com.tein.overcatchbackend.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.tein.overcatchbackend.domain.BaseEntity;
import com.tein.overcatchbackend.enums.PageStatus;
import com.tein.overcatchbackend.enums.SessionStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "BANK_TRANSACTION")
//@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BankTransaction extends BaseEntity {

    @Column(name = "ACCOUNT_NAME")
    private String accountName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "START_DATE")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "PREVIOUS_BALANCE")
    private String previousBalance;

    @Column(name = "TOTAL_MONEY_IN")
    private Float totalMoneyIn;

    @Column(name = "TOTAL_MONEY_OUT")
    private Float totalMoneyOut;

    @Column(name = "STATEMENT_TYPE")
    private String statementType;

    @Column(name = "CLIENT_TYPE")
    private String clientType;

    @Column(name = "PDF_NAME")
    private String pdfName;

    @Column(name = "BANK_TYPE")
    private String bankType;

    @Column(name = "IBAN")
    private String iban;

    @Column(name = "NEW_BALANCE")
    private String newBalance;

    @Column(name = "SHORT_CODE")
    private String sortCode;

    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @OneToMany(mappedBy = "bankTransaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BankTransactionDetail> items = new ArrayList<>();

    public void setItems(List<BankTransactionDetail> itemList) {
        this.saveItems(itemList);
    }

    private void saveItems(List<BankTransactionDetail> bankTransactionDetails) {
        if (bankTransactionDetails != null) {
            this.items = new ArrayList<>();
            bankTransactionDetails.forEach(item -> {
                this.items.add(item);
                item.setBankTransaction(this);
            });
        }
    }

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "DOCUMENT_ID", referencedColumnName = "id")
    private Document document;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;
}
