package com.tein.overcatchbackend.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tein.overcatchbackend.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name = "HELP_TYPE")
@EqualsAndHashCode(callSuper = true)
public class HelpType extends BaseEntity {

    @Column(name = "HELP_TYPE_SHOW_NAME")
    private String helpTypeShowName;


    //********************
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DURATION")
    private int duration;
    //********************
    //Git denemesi için eklendi
}
