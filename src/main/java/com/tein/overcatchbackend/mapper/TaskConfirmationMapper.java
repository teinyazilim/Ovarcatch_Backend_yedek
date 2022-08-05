package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.TaskConfirmationDTO;
import com.tein.overcatchbackend.domain.model.TaskConfirmation;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface TaskConfirmationMapper extends BaseConverter<TaskConfirmation, TaskConfirmationDTO> {
}
