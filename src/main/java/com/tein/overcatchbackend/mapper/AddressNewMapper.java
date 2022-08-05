package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.AddressNewDTO;
import com.tein.overcatchbackend.domain.model.AddressNew;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressNewMapper extends BaseConverter<AddressNew, AddressNewDTO> {
    //Bu interface Yeni Ekledim
}
