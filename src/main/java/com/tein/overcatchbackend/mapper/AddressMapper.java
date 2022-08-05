package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.model.Address;
import com.tein.overcatchbackend.domain.dto.AddressDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper extends BaseConverter<Address, AddressDTO> {

}