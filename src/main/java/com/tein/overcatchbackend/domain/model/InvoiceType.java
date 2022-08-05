package com.tein.overcatchbackend.domain.model;

import com.tein.overcatchbackend.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "INVOICE_TYPE")
public class InvoiceType extends BaseEntity {

    @Column(name = "INVOICE_TYPE")
    private String invoiceType;

    @Column(name = "IS_INVOICE_NUMBER")
    private Boolean isInvoiceNumber=true;

    @Column(name = "INVOICE_NUMBER")
    private Integer invoiceNumber=1;

    @Column(name = "SELECTED_INVOICE" )
    private Integer selectedInvoiceType;

    @Column(name = "IS_EMAIL_SEND")
    private Boolean isMailSend;

    @Column(name = "CLIEND_ID")
    private Long clientId;

}
