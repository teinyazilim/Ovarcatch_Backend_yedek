package com.tein.overcatchbackend.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectorDetailDTO  extends BaseDto {

    private Long id;

    private String initial;

    private String name;

    private String surname;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dob;

    private String sex;

    private String maritalStatus;

    private String spouseName;

    private String phoneNumber;

    private String nextOfKinName;

    private String nextOfKinAddress;

    private String nextOfKinNumber;

    private String nextOfKinEmail;

    private String residentailAddress;

    private String businessAddress;

    private String nino;

    private String utr;

    private String email;

    private String visaType;

    private LocalDate visaStartDate;

    private LocalDate visaExpiryDate;


}
