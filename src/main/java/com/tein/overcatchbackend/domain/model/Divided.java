package com.tein.overcatchbackend.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.tein.overcatchbackend.domain.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "DIVIDED")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Divided extends BaseEntity {
    @Column(name = "PAYMENT_NUMBER")
    private Float paymentNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "DATE_PAYMENT_RATE")
    private LocalDate datePaymentRate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "DIVIDED_END_DATE")
    private LocalDate dividedEndDate;

    @Column(name = "SHAREHOLDING")
    private Float shareHolding;

    @Column(name = "AMOUNT_PAYABLE")
    private Float amountPayable;

    @Column(name = "CURRENCY")
    private String currency;

    private Long directorId;

    // Büşra ekleme yaptı
    @Column
    private String fileName;

}
