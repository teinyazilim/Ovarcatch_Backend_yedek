package com.tein.overcatchbackend.mapper;


import com.tein.overcatchbackend.domain.model.Document;
import com.tein.overcatchbackend.domain.dto.DocumentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentMapper extends BaseConverter<Document, DocumentDTO> {

}