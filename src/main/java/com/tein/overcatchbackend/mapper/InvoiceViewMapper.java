package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.InvoiceViewDTO;
import com.tein.overcatchbackend.domain.model.Invoice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoiceViewMapper extends BaseConverter<Invoice, InvoiceViewDTO> {

}
