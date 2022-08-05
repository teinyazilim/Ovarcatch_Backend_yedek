package com.tein.overcatchbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.tein.overcatchbackend.enums.AddressType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class AddressNewDTO extends BaseDto {

    private Long id;
    private String number;

    private String street;

    private String district;

    private String city;

    private String county;

    private String postcode;

    private String country;

    private AddressType addressType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate relocationDate;
    //Bu Class'ı yeni ekledim
}
