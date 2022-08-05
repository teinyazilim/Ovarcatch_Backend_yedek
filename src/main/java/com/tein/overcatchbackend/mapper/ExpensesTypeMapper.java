package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.ExpensesTypeDTO;
import com.tein.overcatchbackend.domain.model.ExpensesType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExpensesTypeMapper extends BaseConverter<ExpensesType, ExpensesTypeDTO>{
}
