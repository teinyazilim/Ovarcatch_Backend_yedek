package com.tein.overcatchbackend.domain.model;

import com.tein.overcatchbackend.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
@Table(name = "CUSTOMER_CLIENT")
@EqualsAndHashCode(onlyExplicitlyIncluded = true,callSuper = true)
public class CustomerClient extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customerInfo;

    @Column(name = "SHARE_PERCENT")
    int sharePercent;


}
