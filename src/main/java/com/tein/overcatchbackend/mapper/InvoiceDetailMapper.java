package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.InvoiceDetailDTO;
import com.tein.overcatchbackend.domain.model.InvoiceDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoiceDetailMapper extends BaseConverter<InvoiceDetail, InvoiceDetailDTO> {

}
