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
@Table(name = "EXPENSES_TYPE")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ExpensesType  extends BaseEntity {

    @Column(name="TYPE")
    private String expensesType;
}
