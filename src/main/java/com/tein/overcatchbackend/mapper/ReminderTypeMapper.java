package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.ReminderTypeDTO;
import com.tein.overcatchbackend.domain.model.ReminderType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReminderTypeMapper extends BaseConverter <ReminderType, ReminderTypeDTO> {
}
