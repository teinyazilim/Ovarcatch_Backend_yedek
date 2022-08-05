package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.BuyerDTO;
import com.tein.overcatchbackend.domain.model.Buyer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BuyerMapper extends BaseConverter<Buyer, BuyerDTO> {

}
