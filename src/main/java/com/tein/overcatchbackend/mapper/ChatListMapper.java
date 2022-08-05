package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.ChatListDTO;
import com.tein.overcatchbackend.domain.model.ChatList;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatListMapper extends BaseConverter<ChatList, ChatListDTO> {

}
