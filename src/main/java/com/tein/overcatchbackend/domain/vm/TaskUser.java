package com.tein.overcatchbackend.domain.vm;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;
import java.time.LocalDate;


//@JsonIgnoreProperties(ignoreUnknown = true) // security warning fix
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class TaskUser {

    private Long id;

    private String name;

    @Lob
    private byte[] photo;

    private String surname;

    private String email;

    private String msisdn;

    private String userType;

    //Yeni Eklendi ...
    private String userlanguage;
}
