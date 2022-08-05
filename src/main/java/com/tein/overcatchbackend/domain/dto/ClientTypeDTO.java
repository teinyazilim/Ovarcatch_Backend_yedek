package com.tein.overcatchbackend.domain.dto;

import com.tein.overcatchbackend.enums.AgreementType;
import com.tein.overcatchbackend.enums.ClientTypeEnum;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientTypeDTO implements Serializable{

    private Long id;

    private ClientTypeEnum clientTypeEnum;

    private int code;

    private String visaType;

    private AgreementType agreementType;
}