package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.TaskDTO;
import com.tein.overcatchbackend.domain.model.Tasks;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper extends BaseConverter<Tasks, TaskDTO>{
}
