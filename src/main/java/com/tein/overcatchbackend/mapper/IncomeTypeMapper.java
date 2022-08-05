package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.IncomeTypeDTO;
import com.tein.overcatchbackend.domain.model.IncomeType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IncomeTypeMapper extends BaseConverter<IncomeType, IncomeTypeDTO>{
}
