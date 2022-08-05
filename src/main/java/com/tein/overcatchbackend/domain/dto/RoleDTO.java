package com.tein.overcatchbackend.domain.dto;

import com.tein.overcatchbackend.enums.ModuleTypeEnum;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO extends BaseDto {

    private String id;

    private String roleCode;

    private String roleDescription;

    private ModuleTypeEnum moduleTypeEnum;
}
