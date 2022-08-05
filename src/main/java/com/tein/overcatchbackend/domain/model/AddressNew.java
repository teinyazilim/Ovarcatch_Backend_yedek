package com.tein.overcatchbackend.domain.model;

import com.tein.overcatchbackend.domain.BaseEntity;
import com.tein.overcatchbackend.enums.AddressType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "ADDRESSNEW")
@EqualsAndHashCode(callSuper = true)
public class AddressNew extends BaseEntity {

    @Column(name = "CITY")
    private String city;

    @Column(name = "DISTRICT")
    private String district;

    @Column(name = "COUNTY")
    private String county;

    @Column(name = "STREET")
    private String street;

    @Column(name = "NUMBER")
    private String number;

    @Column(name = "POSTCODE")
    private String postcode;

    @Column(name = "COUNTRY")
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "ADDRESS_TYPE")
    private AddressType addressType;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @Column(name = "CLIENT_ADDRESS_ID")
    private Long client_address;

    @Column(name = "RELOCATIONDATE")
    private LocalDate relocationDate;
    //Bu Sınıfı yeni ekledim
}
