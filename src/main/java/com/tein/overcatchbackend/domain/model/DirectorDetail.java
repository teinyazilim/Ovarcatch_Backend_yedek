package com.tein.overcatchbackend.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tein.overcatchbackend.domain.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "DIRECTOR_DETAIL")
@EqualsAndHashCode(callSuper = true)
public class DirectorDetail extends BaseEntity {

    @Column(name = "INITIAL")
    private String initial;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CODE")
    private String code;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "NATIONALITY")
    private String nationality;

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

    @Column(name = "NEXT_OF_KEEN")
    private String nextOfKeen;

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

    @Column(name = "NEXT_OF_KIN_NAME")
    private String nextOfKinName;

    @Column(name = "NEXT_OF_KIN_ADDRESS")
    private String nextOfKinAddress;

    @Column(name = "NEXT_OF_KIN_NNUMBER")
    private String nextOfKinNumber;

    @Column(name = "NEXT_OF_Email")
    private String nextOfKinEmail;

    @Column(name = "VISA_TYPE")
    private String visaType;


    @Column(name = "VISA_START_DATE")
    private LocalDate visaStartDate;

    @Column(name = "VISA_EXPIRY_DATE")
    private LocalDate visaExpiryDate;

    @JsonIgnore
    @ManyToMany(mappedBy = "directorDetails")
    private List<Company> companies;


}
