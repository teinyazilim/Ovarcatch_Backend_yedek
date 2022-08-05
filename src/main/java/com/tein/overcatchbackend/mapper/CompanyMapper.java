package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.CompanyDTO;
import com.tein.overcatchbackend.domain.model.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper extends BaseConverter<Company, CompanyDTO> {

}
