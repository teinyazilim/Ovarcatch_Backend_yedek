package com.tein.overcatchbackend.mapper;


import com.tein.overcatchbackend.domain.dto.CashInvoiceDTO;
import com.tein.overcatchbackend.domain.model.CashInvoice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CashInvoiceMapper extends BaseConverter<CashInvoice, CashInvoiceDTO>{
}
