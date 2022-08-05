package com.tein.overcatchbackend.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tein.overcatchbackend.domain.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "PERSONEL")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@SequenceGenerator(name="SEQ", sequenceName="personel_seq", allocationSize=1)
public class Personel extends BaseEntity{
    @JsonIgnore
    @OneToMany(mappedBy = "personel", cascade = CascadeType.ALL)
    private List<TaskConfirmation> taskConfirmations = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "USER_ID",referencedColumnName = "id")
    private User user;

    private String department;

    @ManyToOne
    private Personel parent;

}
