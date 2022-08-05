package com.tein.overcatchbackend.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LastAccountDTO extends BaseDto{
    private String period_end_on;
    private String made_up_to;
    private String period_start_on;
    private String type;
}
