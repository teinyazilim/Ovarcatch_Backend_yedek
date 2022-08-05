package com.tein.overcatchbackend.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tein.overcatchbackend.domain.BaseEntity;
import com.tein.overcatchbackend.enums.LetterStatus;
import com.tein.overcatchbackend.enums.LetterTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import netscape.javascript.JSObject;
import springfox.documentation.spring.web.json.Json;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "LETTER_TYPE")
@EqualsAndHashCode(callSuper = true)
public class LetterType extends BaseEntity {

    @Column(name = "LETTER_TYPE_NAME")
    private String letterTypeName;


    @Column(name = "LETTER_TEMPLATE", length = 10000)
    private String letterTemplate;

    @OneToMany(mappedBy = "letterType")
    @JsonIgnore
    private List<Letter> letter;

    @Column(name = "CLIENT_TYPE")
    private String clientType;

    @Column(name = "USER_ROLE")
    private String userRole;
}
