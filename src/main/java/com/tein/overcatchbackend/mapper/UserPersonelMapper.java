package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.UserDTO;
import com.tein.overcatchbackend.domain.dto.UserPersonelDTO;
import com.tein.overcatchbackend.domain.model.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
//Git denemesi için eklendi
@Mapper(componentModel = "spring")
public interface UserPersonelMapper extends BaseConverter<User, UserPersonelDTO> {

}
