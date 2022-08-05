package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.CustomerClientDTO;
import com.tein.overcatchbackend.domain.dto.CustomerClientDetailDTO;
import com.tein.overcatchbackend.domain.model.CustomerClient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserCompanyMapper extends BaseConverter<CustomerClient, CustomerClientDetailDTO> {

}
