package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.BankTransactionDetailDTO;
import com.tein.overcatchbackend.domain.dto.ClientDTO;
import com.tein.overcatchbackend.domain.dto.NewCustomerClientDTO;
import com.tein.overcatchbackend.domain.model.BankTransactionDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewCustomerClientMapper extends BaseConverter<ClientDTO, NewCustomerClientDTO> {
}
