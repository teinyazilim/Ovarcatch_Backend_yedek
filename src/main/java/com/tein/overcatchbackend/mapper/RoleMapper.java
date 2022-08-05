package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.RoleDTO;
import com.tein.overcatchbackend.domain.model.Roles;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface RoleMapper extends BaseConverter<Roles, RoleDTO> {

}
