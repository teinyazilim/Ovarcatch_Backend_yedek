package com.tein.overcatchbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoticeCreateDTO {

    private Long id;
    private boolean eCAA;
    private boolean app;
    private boolean limited;
    private boolean mail;
    private boolean other;
    private boolean selfAssesment;
    private boolean sms;
    private boolean soleTrade;
    private boolean trading;
    private boolean vat;

    private String subject;
    private String content;

    private List<ClientDTO> clientList;

    //Yeni Eklendi ...
    private DocumentDTO document;

}
