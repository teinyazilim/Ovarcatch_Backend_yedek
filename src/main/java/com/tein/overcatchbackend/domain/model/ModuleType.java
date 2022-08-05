package com.tein.overcatchbackend.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tein.overcatchbackend.domain.BaseEntity;
import com.tein.overcatchbackend.enums.AgreementType;
import com.tein.overcatchbackend.enums.ModuleTypeEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "MODULE_TYPE")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ModuleType extends BaseEntity{

    @JsonIgnore
    @OneToOne(mappedBy = "moduleType",cascade=CascadeType.ALL, orphanRemoval = true)
    private Tasks task;

    @Enumerated(EnumType.STRING)
    @Column(name = "MODULE_TYPE_ENUM")
    private ModuleTypeEnum moduleTypeEnum;

    @JoinColumn(name = "NAME")
    private String name;

    @JsonIgnore
    @ManyToOne
    private ModuleType moduleType;

    @JoinColumn(name = "RESPONSIBLE_EMAIL")
    private String responsibleEmail;

}
