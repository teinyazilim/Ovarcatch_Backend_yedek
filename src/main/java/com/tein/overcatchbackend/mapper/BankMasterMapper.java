package com.tein.overcatchbackend.mapper;
import com.tein.overcatchbackend.domain.dto.BankMasterDTO;
import com.tein.overcatchbackend.domain.model.BankMaster;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankMasterMapper extends BaseConverter<BankMaster, BankMasterDTO>{
}
