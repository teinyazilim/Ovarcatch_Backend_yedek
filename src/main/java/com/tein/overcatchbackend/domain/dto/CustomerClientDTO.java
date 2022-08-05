package com.tein.overcatchbackend.domain.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
public class CustomerClientDTO implements Serializable {


//    private ClientDTO client;

    private CustomerDTO customerInfo;

    private int sharePercent;

}
