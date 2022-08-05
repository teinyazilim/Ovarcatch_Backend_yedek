package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.HelpDTO;
import com.tein.overcatchbackend.domain.dto.HelpTypeDTO;
import com.tein.overcatchbackend.domain.model.Help;
import com.tein.overcatchbackend.domain.model.HelpType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HelpTypeMapper extends BaseConverter<HelpType, HelpTypeDTO> {

}
