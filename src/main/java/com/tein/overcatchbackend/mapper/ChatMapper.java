package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.ChatDTO;
import com.tein.overcatchbackend.domain.model.Chat;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMapper extends BaseConverter<Chat, ChatDTO> {

}
