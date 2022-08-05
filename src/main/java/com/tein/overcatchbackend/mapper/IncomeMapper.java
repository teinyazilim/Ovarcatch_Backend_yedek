package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.IncomeDTO;
import com.tein.overcatchbackend.domain.model.Income;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IncomeMapper extends BaseConverter<Income, IncomeDTO>{
}
