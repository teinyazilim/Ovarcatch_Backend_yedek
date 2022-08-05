package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.ClientDTO;
import com.tein.overcatchbackend.domain.model.Client;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {CompanyMapper.class, FounderOwnerMapper.class,
                DirectorDetailMapper.class, DocumentMapper.class, TaskMapper.class,CustomerClientMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface ClientMapper extends BaseConverter<Client, ClientDTO> {



}
