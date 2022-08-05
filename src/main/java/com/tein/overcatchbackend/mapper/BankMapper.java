package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.BankDTO;
import com.tein.overcatchbackend.domain.model.Bank;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankMapper extends BaseConverter<Bank, BankDTO> {

}