package com.tein.overcatchbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.tein.overcatchbackend.enums.ClientTypeEnum;
import lombok.*;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientDTO implements Serializable {

    private Long id;

    private String notes;

    private Boolean isExisting;

    private Boolean isActive;

    private Boolean isVatMember;

    private String state;

    private String vatNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate vatRegistrationDate;

    private String vatPeriodEnd;

    private ClientTypeEnum clientTypeEnum;

    private String code;

    private String visaType;

    private String agreementType;

    private String clientFolder;

    private String clientFileName;

    private String gateway;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate yearEndDate;

    private List<CustomerClientDTO> customerClients;

    private List<AddressDTO> addressList;

    //  List<AddressNew> addressNewList Yeni Eklendi
    private List<AddressNewDTO> addressNewList;
    // Yukarıdaki Kod Yeni Eklendi .
    private FounderOwnerDTO founderOwner;

    private CompanyDTO company;

    private List<TaskDTO> tasks;

    private List<DocumentDTO> documents;

    private String payment;

    private String web;

    private Integer selectedInvoiceType;

    private Integer bank_id;

    private Boolean status;

    private Boolean status_completed;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate reminderDate;

    private Integer reminderRepeat;

   @ReadOnlyProperty
    private String clientName=getClientName();

    public String getClientName(){
        if (company != null)
            return company.getName();
        else if (founderOwner != null)
            return founderOwner.getTradeAsName();
        else return "";
    }
}

