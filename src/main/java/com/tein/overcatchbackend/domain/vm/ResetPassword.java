package com.tein.overcatchbackend.domain.vm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPassword {
    private String changeCode;
    private String password;
    private String email;
}
