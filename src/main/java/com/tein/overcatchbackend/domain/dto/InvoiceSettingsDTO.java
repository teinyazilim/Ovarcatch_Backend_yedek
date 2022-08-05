package com.tein.overcatchbackend.domain.dto;


import com.tein.overcatchbackend.enums.DocumentType;
import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceSettingsDTO{

    private Long clientId;

    private Boolean isInvoiceNumber;

    private Integer invoiceNumber;

    private Integer selectedInvoiceType;

    private String web;

    private String fileName;

    private String filePath;

    private Boolean isMailSend;

    private String invoiceType;

}
