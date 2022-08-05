package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.BankDTO;
import com.tein.overcatchbackend.domain.dto.PersonelDTO;
import com.tein.overcatchbackend.domain.model.Bank;
import com.tein.overcatchbackend.domain.model.Personel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonelMapper extends BaseConverter<Personel, PersonelDTO> {

}
