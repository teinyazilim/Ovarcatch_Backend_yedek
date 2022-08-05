package com.tein.overcatchbackend.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tein.overcatchbackend.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "NATURE_BUSINESS", indexes = {
        @Index(name = "CODE_X", columnList = "CODE", unique = true)
})
@EqualsAndHashCode(callSuper = true)
public class NatureBusiness extends BaseEntity {


    @Column(name = "CODE")
    private String code;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToMany(mappedBy = "natureBusinesses")
    @JsonIgnore
    private List<Company> incorprationCompanies = new ArrayList<>();

}
