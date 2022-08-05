package com.tein.overcatchbackend.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tein.overcatchbackend.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "INVOICE_DETAIL")
@EqualsAndHashCode(onlyExplicitlyIncluded = true,callSuper = true)
public class InvoiceDetail extends BaseEntity  {

    @Column(name = "ITEM_DESCRIPTION")
    private String itemDescription;

    @Column(name = "QUANTITY")
    private Float quantity;

    @Column(name = "UNIT_PRICE")
    private Float unitPrice;

    @Column(name = "VAT_RATE")
    private Double vatRate;

    @Column(name = "VAT_AMOUNT")
    private Float vatAmount;

    @Column(name = "AMOUNT")
    private Float amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "INVOICE_ID")
    private Invoice invoice;

}
