package com.tein.overcatchbackend.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CustomerClientDetailDTO implements Serializable {


    private ClientDTO client;

//    private CustomerDTO customerInfo;

    private int sharePercent;

}
