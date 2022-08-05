package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.LetterDTO;
import com.tein.overcatchbackend.domain.model.Letter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LetterMapper extends BaseConverter<Letter, LetterDTO> {

}
