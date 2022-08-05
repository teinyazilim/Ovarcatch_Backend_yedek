package com.tein.overcatchbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.tein.overcatchbackend.domain.dto.BaseDto;
import com.tein.overcatchbackend.domain.model.Bank;
import com.tein.overcatchbackend.domain.model.Buyer;
import com.tein.overcatchbackend.domain.model.Client;
import com.tein.overcatchbackend.domain.model.Tasks;
import com.tein.overcatchbackend.enums.InvoiceType;
import com.tein.overcatchbackend.enums.ModuleTypeEnum;
import com.tein.overcatchbackend.enums.TaskConfirmEnum;
import lombok.*;
import org.springframework.data.redis.stream.Task;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceViewDTO extends BaseDto {

    private Long id;

    private String invoiceNumber;

    private String companyNumber;

    private InvoiceType invoiceType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate invoiceDate;

    private Float total;

    private String buyerName;

//    @JsonIgnore
    private BuyerDTO buyer;

//    @JsonIgnore
    private BankDTO bank;

    public void setBuyer(BuyerDTO buyer) {
        this.buyer = new BuyerDTO();
        this.buyer = buyer;
        this.buyerName = buyer.getBuyerName();
    }

    private String invoiceCode;

    private String clientAddress;

    private String clientEmail;

    private String clientPhone;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dueDate;

    private Float vat;

    private Float subTotal;

    private String commercialTitle;

    private String buyerAddress;

    private String buyerPhone;

    private String buyerEmail;

    private String currencyOfPayment;

    @JsonIgnore
    private List<TaskDTO> tasks;

    private int updateState;

    private int deleteState;

    private List<InvoiceDetailDTO> invoiceDetails;

    private Long clientId;

    private String web;

    private Integer selectedInvoiceType;

}
