package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.LetterDTO;
import com.tein.overcatchbackend.domain.dto.LetterTypeDTO;
import com.tein.overcatchbackend.domain.model.Letter;
import com.tein.overcatchbackend.domain.model.LetterType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LetterTypeMapper extends BaseConverter<LetterType, LetterTypeDTO> {

}
