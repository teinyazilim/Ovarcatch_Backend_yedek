package com.tein.overcatchbackend.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.tein.overcatchbackend.domain.model.Client;
import com.tein.overcatchbackend.domain.model.HelpType;
import com.tein.overcatchbackend.enums.PriorityLevel;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HelpTypeDTO extends BaseDto {


    private Long id;

    private String helpTypeShowName;


    //********************
    private String email;

    private int duration;

    //********************
    //Git denemesi için eklendi

}
