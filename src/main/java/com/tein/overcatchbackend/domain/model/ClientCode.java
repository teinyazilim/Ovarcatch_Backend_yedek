package com.tein.overcatchbackend.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class ClientCode {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    Long id;

    @Column(name = "SEQ_NAME")
    private String seqName;

    @Column(name = "SEQ_VALUE")
    private String seqValue;
}
