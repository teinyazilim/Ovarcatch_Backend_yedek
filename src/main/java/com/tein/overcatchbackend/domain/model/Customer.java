package com.tein.overcatchbackend.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tein.overcatchbackend.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "CUSTOMER")
@EqualsAndHashCode(callSuper = true)
public class Customer extends BaseEntity{

    @Column(name = "BRP_NUMBER")
    private String brpNumber;

    @Column(name = "BRP_EXPIRE_DATE")
    private LocalDate brpExpireDate;

    @JsonIgnore
    @OneToMany(mappedBy = "customerInfo")
    private Set<CustomerClient> customerClients;

    @OneToOne
    @JoinColumn(name = "USER_ID",referencedColumnName = "id")
    private User user;

}
