package com.tein.overcatchbackend.domain.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import java.io.Serializable;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankMasterDTO extends BaseDto  {

    private Long id;
    private String name;

}

