package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.BankTransactionDTO;
import com.tein.overcatchbackend.domain.dto.HelpViewDTO;
import com.tein.overcatchbackend.domain.model.BankTransaction;
import com.tein.overcatchbackend.mapper.BankTransactionMapper;
import com.tein.overcatchbackend.repository.BankTransactionRepository;
import com.tein.overcatchbackend.service.BankTransactionService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class BankTransactionResource {


    private final BankTransactionService bankTransactionService;
    private final BankTransactionMapper bankTransactionMapper;
    private final BankTransactionRepository bankTransactionRepository;

    @RequestMapping(value = "/banktransaction", method = RequestMethod.POST)
    public BankTransaction saveBankTransaction(@RequestBody BankTransactionDTO bankTransactionDTO) {
        BankTransaction bankTransaction= bankTransactionMapper.toEntity(bankTransactionDTO);
        return bankTransactionService.saveBankTransaction(bankTransaction);
    }


    @RequestMapping(value = "/getbanktransactions", method= RequestMethod.GET)
    public List<BankTransactionDTO> getBankTransactionsByClientId(@RequestParam("clientId") Long clientId) {
        return bankTransactionService.getBankTransactionsByClientId(Long.valueOf(clientId));
    }


    @RequestMapping(value = "/banktransactions", method = RequestMethod.GET)
    public List<BankTransactionDTO> getBankTransactions() {
        return bankTransactionMapper.toDto(bankTransactionService.getTransactions());
    }

}
