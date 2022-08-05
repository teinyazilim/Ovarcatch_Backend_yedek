package com.tein.overcatchbackend.domain.model;

import com.tein.overcatchbackend.domain.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "INCOME_TYPE")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class IncomeType extends BaseEntity {

    @Column(name="TYPE")
    private String incomesType;

}
