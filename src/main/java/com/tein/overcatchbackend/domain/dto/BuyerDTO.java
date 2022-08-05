package com.tein.overcatchbackend.domain.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyerDTO extends BaseDto {

    private Long id;

    private String buyerName;

    private String commercialTitle;

    private String buyerAddress;

    private String buyerEmail;

    private String buyerPhone;

    private String accountType;

    private Long clientId;

    private Long vatNumber;

    private Long companyRegister;

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
