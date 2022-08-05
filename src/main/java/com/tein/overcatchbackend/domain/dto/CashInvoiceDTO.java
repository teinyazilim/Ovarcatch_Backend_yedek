package com.tein.overcatchbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.tein.overcatchbackend.domain.model.CashInvoice;
import com.tein.overcatchbackend.domain.model.Client;
import lombok.*;


import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CashInvoiceDTO extends BaseDto{

    private Long id;

    private Long cashInvoiceTypeId;

    private Float price;

    private String currencyOfPayment;

    private Client client;

    private Integer updateState;

    private String fileName;

    private String cashInvoiceType;

    private String filePath;

    private Boolean isActive;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate invoiceDate;

    private TaskDTO task;

    private Integer deleteState;
}
