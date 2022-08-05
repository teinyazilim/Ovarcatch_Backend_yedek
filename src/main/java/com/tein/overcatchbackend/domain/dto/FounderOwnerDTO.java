package com.tein.overcatchbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FounderOwnerDTO extends BaseDto {

//    @JsonFormat(pattern = "yyyy-MM-dd")
//    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @JsonSerialize(using = LocalDateSerializer.class)
//    private LocalDate visaDateIssue;
//
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @JsonSerialize(using = LocalDateSerializer.class)
//    private LocalDate visaExpiryDate;
    private Long id;
    private String initial;

    private String name;

    private String surname;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    private String sex;

    private String tradeAsName;

    private String maritalStatus;

    private String spouseName;

    private String phoneNumber;

    private String gateway;

    private String nextOfKinName;

    private String nextOfKinAddress;

    private String nextOfKinNumber;

    private String nextOfKinEmail;

    private String residentailAddress;

    private String businessAddress;

    private String nino;

    private String payeNumber;

    private String utr;

    private String email;

    private String visaType;

    private String eoriNumber;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate visaStartDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate visaExpiryDate;

    private String numberOfDependence;
    //private CompanyDTO companyInfo;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate workStartDate;

}
