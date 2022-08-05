package com.tein.overcatchbackend.mapper;


import com.tein.overcatchbackend.domain.model.ModuleType;
import com.tein.overcatchbackend.domain.dto.ModuleTypeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModuleTypeMapper extends BaseConverter<ModuleType, ModuleTypeDTO> {

}