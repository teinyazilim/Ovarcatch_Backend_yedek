package com.tein.overcatchbackend.domain.dto;

import com.tein.overcatchbackend.domain.BaseEntity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReminderTypeDTO extends BaseEntity {

    private Long id;

    private String reminderTypeName;

    private String reminderTemplate;

    private String reminderClientType;

    private Boolean isActive;


}
