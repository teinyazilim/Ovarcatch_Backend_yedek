package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.DirectorDetailDTO;
import com.tein.overcatchbackend.domain.model.DirectorDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DirectorDetailMapper extends BaseConverter<DirectorDetail, DirectorDetailDTO> {

}
