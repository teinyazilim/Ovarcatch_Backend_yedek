package com.tein.overcatchbackend.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.tein.overcatchbackend.enums.LetterStatus;
import com.tein.overcatchbackend.enums.LetterTypeEnum;
import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Base64;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LetterTypeDTO implements Serializable {

    private Long id;

    private String letterTypeName;


    private String letterTemplate;

    private String clientType;

    private String userRole;


}
