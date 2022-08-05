package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.CustomerClientDTO;
import com.tein.overcatchbackend.domain.dto.CustomerClientDetailDTO;
import com.tein.overcatchbackend.domain.model.CustomerClient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerClientDetailMapper extends BaseConverter<CustomerClient, CustomerClientDetailDTO> {

}
