package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.NatureBusinessDTO;
import com.tein.overcatchbackend.domain.model.NatureBusiness;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NatureBusinessMapper extends BaseConverter<NatureBusiness, NatureBusinessDTO> {

}
