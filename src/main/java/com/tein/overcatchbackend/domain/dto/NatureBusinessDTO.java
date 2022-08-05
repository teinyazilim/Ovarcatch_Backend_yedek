package com.tein.overcatchbackend.domain.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NatureBusinessDTO extends BaseDto {

    private Long id;

    private String code;

    private String description;

}
