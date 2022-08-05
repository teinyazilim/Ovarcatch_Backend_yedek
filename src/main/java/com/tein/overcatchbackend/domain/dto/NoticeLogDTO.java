package com.tein.overcatchbackend.domain.dto;

import com.tein.overcatchbackend.domain.BaseEntity;
import com.tein.overcatchbackend.domain.model.Document;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeLogDTO extends BaseEntity {
    String notiType;
    String notiTo;
    String message;
    Integer status;
    String subject;
    Document document;
    // Entitty 'de kullandığımız verilerin front-End gözükmesini istersek !
    // DTO ' ya aynı veriyi buraya eklememiz gerekiyor .

    //    UserDTO user;
    //    ClientDTO client;
}