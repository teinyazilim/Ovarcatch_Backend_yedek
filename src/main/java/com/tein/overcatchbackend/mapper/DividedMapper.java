package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.DividedDTO;
import com.tein.overcatchbackend.domain.model.Divided;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DividedMapper extends BaseConverter<Divided, DividedDTO> {
}
