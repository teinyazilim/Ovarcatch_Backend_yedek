package com.tein.overcatchbackend.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.tein.overcatchbackend.domain.model.Document;
import com.tein.overcatchbackend.domain.model.Tasks;
import com.tein.overcatchbackend.domain.model.User;
import com.tein.overcatchbackend.enums.PriorityLevel;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HelpDTO extends BaseDto {


    private Long id;

    private HelpTypeDTO helpType;

    private String description;

    private String answer;

    private PriorityLevel priorityLevel;

    private DocumentDTO document;

    private ClientDTO client;

    private TaskDTO task;

    private UserDTO request_user;

    private String divided;

}
