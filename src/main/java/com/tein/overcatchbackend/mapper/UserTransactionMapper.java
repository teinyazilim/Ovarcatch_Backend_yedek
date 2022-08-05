package com.tein.overcatchbackend.mapper;

import com.tein.overcatchbackend.domain.dto.UserTransactionDTO;
import com.tein.overcatchbackend.domain.model.UserTransaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserTransactionMapper extends BaseConverter<UserTransaction, UserTransactionDTO> {

}
