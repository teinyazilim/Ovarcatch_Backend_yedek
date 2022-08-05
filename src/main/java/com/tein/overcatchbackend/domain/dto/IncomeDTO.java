package com.tein.overcatchbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.tein.overcatchbackend.domain.model.Client;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IncomeDTO extends BaseDto {

    private Long id;

    private Long incomeTypeId;

    private Float price;

    private String currencyOfPayment;

    private Client client;

    private Integer updateState;

    private String fileName;

    private String incomeType;

    private String filePath;

    private Boolean isActive;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate incomeDate;

    private TaskDTO task;

    private Integer deleteState;
}
