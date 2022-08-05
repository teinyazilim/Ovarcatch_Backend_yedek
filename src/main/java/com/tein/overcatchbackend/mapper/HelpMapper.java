package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.HelpDTO;
import com.tein.overcatchbackend.domain.model.Help;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

//Git denemesi için eklendi
@Mapper(componentModel = "spring")
public interface HelpMapper extends BaseConverter<Help, HelpDTO> {

}
