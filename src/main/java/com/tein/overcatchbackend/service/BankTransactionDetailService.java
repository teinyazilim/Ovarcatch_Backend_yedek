package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.dto.BankTransactionDTO;
import com.tein.overcatchbackend.domain.dto.BankTransactionDetailDTO;
import com.tein.overcatchbackend.domain.model.BankTransaction;
import com.tein.overcatchbackend.domain.model.BankTransactionDetail;
import com.tein.overcatchbackend.mapper.BankTransactionDetailMapper;
import com.tein.overcatchbackend.repository.BankTransactionDetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BankTransactionDetailService {

    private final BankTransactionDetailRepository bankTransactionDetailRepository;
private final BankTransactionDetailMapper bankTransactionDetailMapper;

    public BankTransactionDetail saveBankTransactionDetail(BankTransactionDetail bankTransactionDetail)
    { return bankTransactionDetailRepository.save(bankTransactionDetail);
    }

    public List<BankTransactionDetailDTO> getTransactionById(Long transactionID) {
        List<BankTransactionDetail> bankTransactionDetails = bankTransactionDetailRepository.findAllByBankTransactionId(transactionID);
        List<BankTransactionDetailDTO> bankTransactionDetailDTOS = bankTransactionDetailMapper.toDto(bankTransactionDetails);
        return bankTransactionDetailDTOS;
    }


}

