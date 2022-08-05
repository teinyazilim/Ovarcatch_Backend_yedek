package com.tein.overcatchbackend.domain;

import com.tein.overcatchbackend.enums.ModuleTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class ModuleTypesResponse {

    @Enumerated(EnumType.STRING)
    @Column(name = "module_type_enum")
    private ModuleTypeEnum moduleTypeEnum;

    @Column(name = "confirmation")
    private String confirmation;

    @Column(name = "deger")
    private int deger;

    @Id
    @Column(name = "id")
    private int id;

}
