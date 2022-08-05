package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.FounderOwnerDTO;
import com.tein.overcatchbackend.domain.model.FounderOwner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FounderOwnerMapper extends BaseConverter<FounderOwner, FounderOwnerDTO> {

}
