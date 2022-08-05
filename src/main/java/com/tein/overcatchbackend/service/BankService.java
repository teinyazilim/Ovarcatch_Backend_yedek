package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.dto.BankDTO;
import com.tein.overcatchbackend.domain.dto.BankMasterDTO;
import com.tein.overcatchbackend.domain.dto.BuyerDTO;
import com.tein.overcatchbackend.domain.model.Bank;
import com.tein.overcatchbackend.domain.model.BankMaster;
import com.tein.overcatchbackend.domain.model.Buyer;
import com.tein.overcatchbackend.domain.model.ExpensesType;
import com.tein.overcatchbackend.domain.model.Invoice;
import com.tein.overcatchbackend.mapper.BankMapper;
import com.tein.overcatchbackend.mapper.BankMasterMapper;
import com.tein.overcatchbackend.mapper.BuyerMapper;
import com.tein.overcatchbackend.repository.BankRepository;
import com.tein.overcatchbackend.repository.BankMasterRepository;
import com.tein.overcatchbackend.repository.BuyerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BankService {

    private final BankRepository bankRepository;
    private final BankMapper bankMapper;
    private final BankMasterRepository bankMasterRepository;
    private final BankMasterMapper bankMasterMapper;

    public BankDTO saveBankDetail(BankDTO bankDTO)
    {
        Bank bank = bankMapper.toEntity(bankDTO);
        bank.setIsActive(true);
        return bankMapper.toDto(bankRepository.save(bank));
    }
    public List<BankDTO> getAllBankByClientId(Long clientId)
    {
        return bankMapper.toDto(bankRepository.findAllByClientId(clientId));
    }

    public BankDTO getBankDetail(Long id){
        return bankMapper.toDto(bankRepository.findById(id).get());
    }
    public ResponseEntity<?> deleteBankById(Long id){
        bankRepository.deleteBuyerById(id);
        return ResponseEntity.ok("Success");
    }
    public Integer controlBankForDelete(Long client_id, Long bank_id){
       int i = bankRepository.controlBankForDelete(client_id,bank_id);
        return i;
    }
    public Integer controlBankMasterForDelete(String bank_name){
        int i = bankMasterRepository.controlBankMasterForDelete(bank_name);
        return i;
    }



    public List<BankMaster> getBankMaster(){
        List<BankMaster> bankMasterNames = bankMasterRepository.findAllActive();
        return bankMasterNames;
    }
    public void saveBankMaster(BankMaster bankMaster) {
        BankMaster bankMaster1 = new BankMaster();
        bankMaster1.setName(bankMaster.getName());
        bankMaster1.setIsActive(true);
     bankMasterRepository.save(bankMaster1);
    }
    public ResponseEntity<?> deleteBankMaster(Long id) {
        bankMasterRepository.deleteBankMaster(id);
        return ResponseEntity.ok("Success");
    }

    public BankMaster updateBankMaster(BankMaster bankMaster) {
        bankMaster.setIsActive(true);
        bankMasterRepository.save(bankMaster);
        return bankMaster;
    }

    public Optional<BankMaster> getBankMasterById(Long id){
        return bankMasterRepository.findById(id);
    }

    }


