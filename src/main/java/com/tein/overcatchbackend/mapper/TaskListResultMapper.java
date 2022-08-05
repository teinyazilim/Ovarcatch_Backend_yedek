package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.TaskListResult;
import com.tein.overcatchbackend.domain.dto.TaskListResultDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskListResultMapper extends BaseConverter<TaskListResult, TaskListResultDTO>{
}
