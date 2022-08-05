package com.tein.overcatchbackend.domain.dto;

import com.tein.overcatchbackend.domain.model.Client;
import com.tein.overcatchbackend.domain.model.Help;
import com.tein.overcatchbackend.enums.DocumentType;
import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDTO extends BaseDto {

    private Long id;

    private String processId;

    private Long userID;

    private String documentName;

    private String fileName;

    private String filePath;

    private String fileDescription;

    private DocumentType documentType;

    private Boolean isActive;

    private Long clientId;

}
