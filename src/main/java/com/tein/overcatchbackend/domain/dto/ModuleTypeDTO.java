package com.tein.overcatchbackend.domain.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tein.overcatchbackend.domain.model.ModuleType;
import com.tein.overcatchbackend.domain.model.Tasks;
import com.tein.overcatchbackend.enums.ModuleTypeEnum;
import lombok.*;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModuleTypeDTO implements Serializable {

    private Long id;
    private String name;
    private String moduleTypeEnum;
    private ModuleType moduleType;
    private String responsibleEmail;
    private Long moduleTypeId;
}
