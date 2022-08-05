package com.tein.overcatchbackend.domain.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tein.overcatchbackend.domain.model.Client;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class BankDTO extends BaseDto {

    private Long id;

    private String accountIBAN;

    private Integer accountNumber;

    private String bankName;

    private String sortCode;

    private Long clientId;

    @JsonIgnore
    private ClientDTO client;

    public void setClientId(Long clientId) {
        this.clientId=clientId;
        this.client=new ClientDTO();
        this.client.setId(this.getClientId());
    }

    public void setClient(ClientDTO client){
        this.clientId=client.getId();
        this.client=new ClientDTO();
        this.client=client;
    }

}
