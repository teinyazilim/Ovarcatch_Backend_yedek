package com.tein.overcatchbackend.domain.dto;


import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewInvoiceDetailDTO {

    private String invoiceCode;

    private String address;

    private byte[] photo;

    private String email;

    private String phone;

    private String clientName;

}
