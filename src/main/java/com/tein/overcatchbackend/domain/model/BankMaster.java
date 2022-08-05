package com.tein.overcatchbackend.domain.model;
import com.tein.overcatchbackend.domain.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "BANK_MASTER")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class BankMaster  extends BaseEntity {

    @Column(name = "name")
    private String name;
}