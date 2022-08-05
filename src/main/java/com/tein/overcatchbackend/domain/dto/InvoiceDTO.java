package com.tein.overcatchbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.tein.overcatchbackend.enums.InvoiceType;
import lombok.*;
import org.apache.poi.hpsf.Blob;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO extends BaseDto{

    private Long id;

    private String invoiceCode;

    private String clientName;

    private String companyNumber;

    private InvoiceType invoiceType;

    private String clientAddress;

    private String clientEmail;

    private String clientPhone;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate invoiceDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dueDate;

    private Float vat;

    private Float total;

    private Float subTotal;

    private String buyerName;

    private String commercialTitle;

    private String buyerAddress;

    private String buyerPhone;

    private String buyerEmail;

    private String currencyOfPayment;

    private List<InvoiceDetailDTO> invoiceDetails;

    private String web;
//    @JsonIgnore
    private ClientDTO client;

    private BankDTO bank;

    private Long update_state;

    private Long delete_state;

    private String pdf;
//
//    public void setClientId(Long clientId) {
//        this.clientId = clientId;
//        this.client=new ClientDTO();
//        this.client.setId(clientId);
//    }

}
