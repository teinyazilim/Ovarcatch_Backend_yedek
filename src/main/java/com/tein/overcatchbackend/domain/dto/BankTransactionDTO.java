package com.tein.overcatchbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.tein.overcatchbackend.domain.model.ChatUser;
import com.tein.overcatchbackend.enums.PageStatus;
import com.tein.overcatchbackend.enums.SessionStatus;
import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankTransactionDTO extends BaseDto {

    private Long id;

    private String accountName;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate endDate;

    private Float totalMoneyIn;

    private Float totalMoneyOut;

    private String previousBalance;

    private String iban;

    private String newBalance;

    private String sortCode;

    private String accountNumber;

    private PageStatus pageStatus;

    private SessionStatus sessionStatus;

    private DocumentDTO document;

    private String statementType;

    private String businessName;

    //@JsonIgnore
    private ClientDTO client;

    private String clientType;

    private String pdfName;

    private String bankType;
    public void setClient(ClientDTO client) {
        if(client!=null){
            this.businessName=client.getCompany()==null?client.getFounderOwner().getTradeAsName():client.getCompany().getName();
        }
    }
//    private List<BankTransactionDetailDTO> items;
}
