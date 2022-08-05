package com.tein.overcatchbackend.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.tein.overcatchbackend.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "BANK_TRANSACTION_DETAIL")
@EqualsAndHashCode(callSuper = true)
public class BankTransactionDetail extends BaseEntity {

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "PROCESS_DATE")
    private LocalDate processDate;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TRANSACTION_TYPE")
    private String transactionType;

    @Column(name = "MONEY_IN")
    private String moneyIn;

    @Column(name = "MONEY_OUT")
    private String moneyOut;

    @Column(name = "BALANCE")
    private String balance;

    @ManyToOne
    @JoinColumn(name = "BANK_TRANSACTION_ID", referencedColumnName = "id")
    private BankTransaction bankTransaction;

}
