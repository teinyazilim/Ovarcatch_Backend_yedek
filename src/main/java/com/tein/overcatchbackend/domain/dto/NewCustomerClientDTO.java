package com.tein.overcatchbackend.domain.dto;


import com.tein.overcatchbackend.enums.AddressType;
import com.tein.overcatchbackend.enums.AgreementType;
import com.tein.overcatchbackend.enums.ClientTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewCustomerClientDTO extends BaseDto {

    private String notes;

    private String visaType;

    private ClientTypeEnum clientTypeEnum;

    private Boolean isExisting;

    private AgreementType agreementType;

    private String payment;

    private Long userId;

}