package com.tein.overcatchbackend.mapper;


import com.tein.overcatchbackend.domain.model.BankTransaction;
import com.tein.overcatchbackend.domain.dto.BankTransactionDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring",
        uses = {BankTransactionDetailMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface BankTransactionMapper extends BaseConverter<BankTransaction, BankTransactionDTO>  {
}
