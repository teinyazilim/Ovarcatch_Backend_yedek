package com.tein.overcatchbackend.domain.dto;

import com.tein.overcatchbackend.domain.model.Tasks;
import com.tein.overcatchbackend.enums.PriorityLevel;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HelpViewDTO extends BaseDto {


    private Long id;

    private HelpTypeDTO helpType;

    private String description;

    private String answer;

    private PriorityLevel priorityLevel;

    private DocumentDTO document;

//    private TaskDTO task;
}
