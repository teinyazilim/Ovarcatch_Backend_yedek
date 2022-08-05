package com.tein.overcatchbackend.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDetailDTO extends BaseDto{
    private Long id;

    private String itemDescription;

    private Float quantity;

    private Float unitPrice;

    private Double vatRate;

    private Float vatAmount;

    private Float amount;

}
