package com.tein.overcatchbackend.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.tein.overcatchbackend.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "FOUNDER_OWNER")
@EqualsAndHashCode(callSuper = true)
public class FounderOwner extends BaseEntity {

    @Column(name = "INITIAL")
    private String initial;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "EORI_NUMBER")
    private String eoriNumber;

    @Column(name = "GATEWAY_ID")
    private String gateway;

    @Column(name = "TRADE_AS_NAME")
    private String tradeAsName;

    @Column(name = "PAYE_NUMBER")
    private String payeNumber;

    @Column(name = "DOB")
    private LocalDate dob;

    @Column(name = "SEX")
    private String sex;

    @Column(name = "MARITAL_STATUS")
    private String maritalStatus;

    @Column(name = "SPOUSE_NAME")
    private String spouseName;

    @Column(name = "PHONE")
    private String phoneNumber;

    @Column(name = "NEXT_OF_KIN_NAME")
    private String nextOfKinName;

    @Column(name = "NEXT_OF_KIN_ADDRESS")
    private String nextOfKinAddress;

    @Column(name = "NEXT_OF_KIN_NUMBER")
    private String nextOfKinNumber;

    @Column(name = "NEXT_OF_Email")
    private String nextOfKinEmail;

    @Column(name = "RESIDENTAIL_ADDRESS")
    private String residentailAddress;

    @Column(name = "BUSINESS_ADDRESS")
    private String businessAddress;

    @Column(name = "NINO")
    private String nino;

    @Column(name = "UTR")
    private String utr;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "NATIONALITY")
    private String nationality;

    @Column(name = "VISA_TYPE")
    private String visaType;

    @Column(name = "VISA_START_DATE")
    private LocalDate visaStartDate;

    @Column(name = "VISA_EXPIRY_DATE")
    private LocalDate visaExpiryDate;

    @Column(name = "NUMBER_OF_DEPENDENCE")
    private String numberOfDependence;

    @OneToOne
    @JoinColumn(name= "CLIENT_ID",referencedColumnName = "id")
    private Client clientInfo;

    @Column(name = "WORK_START_DATE")
    private LocalDate workStartDate;

}
