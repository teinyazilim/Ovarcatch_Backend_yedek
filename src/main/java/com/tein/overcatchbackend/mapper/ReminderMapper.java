package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.ReminderDTO;
import com.tein.overcatchbackend.domain.model.Reminder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReminderMapper extends BaseConverter <Reminder, ReminderDTO>{
}
