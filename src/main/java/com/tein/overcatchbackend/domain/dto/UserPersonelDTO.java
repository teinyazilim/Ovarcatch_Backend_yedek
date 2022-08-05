package com.tein.overcatchbackend.domain.dto;

import com.tein.overcatchbackend.enums.UserType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPersonelDTO extends BaseDto {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private String msisdn;

    private String photoURL;

    private String department;

    private UserType userType;
}
