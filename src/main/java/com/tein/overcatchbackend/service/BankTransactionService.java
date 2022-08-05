package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.dto.BankTransactionDTO;
import com.tein.overcatchbackend.domain.model.BankTransaction;
import com.tein.overcatchbackend.mapper.BankTransactionMapper;
import com.tein.overcatchbackend.repository.BankTransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BankTransactionService {

    private final BankTransactionRepository bankTransactionRepository;
    private final BankTransactionMapper bankTransactionMapper;


    public BankTransaction saveBankTransaction(BankTransaction bankTransaction) {
        return bankTransactionRepository.save(bankTransaction);
    }

    public List<BankTransactionDTO> getBankTransactionsByClientId(long clientId) {
        List<BankTransaction> bankTransactions = bankTransactionRepository.findAllByClientId(clientId);
        List<BankTransactionDTO> bankTransactionDTOS = bankTransactionMapper.toDto(bankTransactions);
        return bankTransactionDTOS;
    }
    public List<BankTransaction> getTransactions(){
        return bankTransactionRepository.findAll();

    }

}

