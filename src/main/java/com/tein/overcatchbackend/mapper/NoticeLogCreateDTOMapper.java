package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.NoticeCreateDTO;
import com.tein.overcatchbackend.domain.dto.NoticeLogDTO;
import com.tein.overcatchbackend.domain.model.NoticeLog;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {
            CompanyMapper.class,
            FounderOwnerMapper.class,
            DirectorDetailMapper.class,
            DocumentMapper.class,
            TaskMapper.class,
            CustomerClientMapper.class
        },
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface NoticeLogCreateDTOMapper extends BaseConverter<NoticeLog, NoticeCreateDTO> {
}
