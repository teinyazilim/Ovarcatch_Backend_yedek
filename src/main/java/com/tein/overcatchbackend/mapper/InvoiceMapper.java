package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.InvoiceDTO;
import com.tein.overcatchbackend.domain.model.Invoice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvoiceMapper extends BaseConverter<Invoice, InvoiceDTO> {

}
