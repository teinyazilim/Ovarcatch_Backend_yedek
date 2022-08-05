package com.tein.overcatchbackend.domain.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.tein.overcatchbackend.domain.BaseEntity;
import com.tein.overcatchbackend.enums.CashInvoiceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "CASH_INVOICE")
@EqualsAndHashCode(callSuper = true)
public class CashInvoice extends BaseEntity {

    @Column(name = "CASH_INVOICE_TYPE_ID")
    private Long cashInvoiceTypeId;

    @Column(name = "PRICE")
    private Float price;

    @Column(name = "CURRENCY_CODE")
    private String currencyOfPayment;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "CASH_INVOICE_DATE")
    private LocalDate invoiceDate;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "CASH_INVOICE_TYPE")
    private String cashInvoiceType;

    @Column(name = "FILE_PATH")
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID",referencedColumnName = "id")
    @JsonIgnore
    private Client client;

    @Column(name = "UPDATE_STATE")
    private Integer updateState;
    // update ve delete state kayıt ederken default 0
    // kullanıcı request oluşturursa state 1
    // manager rejected ederse state 2
    // manager done ederse state 3
    // kullanıcı işlemi yapınca default değere döner update 0
    // delete 4 (silinmiş)
    @Column(name = "DELETE_STATE")
    private Integer deleteState;

    @Column(name = "USER_FOLDER")
    private String userFolder;

    @OneToOne
    @JoinColumn(name = "TASK_ID")
    private Tasks task;

}
