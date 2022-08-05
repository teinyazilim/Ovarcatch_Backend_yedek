package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.model.BankTransactionDetail;
import com.tein.overcatchbackend.domain.dto.BankTransactionDetailDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankTransactionDetailMapper extends BaseConverter<BankTransactionDetail, BankTransactionDetailDTO> {
}
