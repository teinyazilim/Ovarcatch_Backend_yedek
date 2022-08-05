package com.tein.overcatchbackend.resource;

import com.spire.ms.System.Exception;
import com.tein.overcatchbackend.domain.dto.BankDTO;
import com.tein.overcatchbackend.domain.dto.BankMasterDTO;
import com.tein.overcatchbackend.domain.dto.ExpensesTypeDTO;
import com.tein.overcatchbackend.domain.model.BankMaster;
import com.tein.overcatchbackend.domain.model.ExpensesType;
import com.tein.overcatchbackend.mapper.BankMasterMapper;
import com.tein.overcatchbackend.service.BankService;
import com.tein.overcatchbackend.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bank")
@RequiredArgsConstructor
public class BankResource {

    private final BankService bankService;
    private final InvoiceService inviceServis;
    private final BankMasterMapper bankMasterMapper;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BankDTO saveBuyer(@RequestBody BankDTO bankDTO) {
        if(bankDTO.getId()==null){
            return bankService.saveBankDetail(bankDTO);
        }else{
            return bankService.saveBankDetail(bankDTO);
        }
    }

    @RequestMapping(value = "/allbyclientid", method = RequestMethod.GET)
    public List<BankDTO> getAllByClientId(@RequestParam("clientId") String clientId) {
        return bankService.getAllBankByClientId(Long.valueOf(clientId));
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public BankDTO getBuyerByClient(@RequestParam("bankId") String buyerId) {
        return bankService.getBankDetail(Long.valueOf(buyerId));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<?> deleteBuyerById(@RequestParam("bankId") String bankId) {
        try {
            bankService.deleteBankById(Long.valueOf(bankId));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

    @RequestMapping(value = "/controlBankForDelete", method = RequestMethod.GET)
    public Integer controlBankForDelete(@RequestParam("client_id") String client_id,
                                        @RequestParam("bank_id") String bank_id){
        return bankService.controlBankForDelete(Long.parseLong(client_id), Long.parseLong(bank_id)) ;
    }
    @RequestMapping(value = "/controlBankMasterForDelete", method = RequestMethod.GET)
    public Integer controlBankMasterForDelete(@RequestParam("bank_name") String bank_name){
        return bankService.controlBankMasterForDelete(bank_name);


    }

    @RequestMapping(value = "/saveBankMaster", method = RequestMethod.POST)
    public void saveBankMaster(@RequestBody BankMaster bankMaster) {
        try {
            bankService.saveBankMaster(bankMaster);
        }
        catch (Exception e) {}
        }


    @RequestMapping(value = "/updateBankName", method = RequestMethod.POST)
    public BankMaster updateCompany(@RequestBody BankMasterDTO bankMasterDTO) {
        BankMaster bankMaster = bankMasterMapper.toEntity(bankMasterDTO);
        return bankService.updateBankMaster(bankMaster);
    }


    @RequestMapping(value = "/deleteBankMaster", method = RequestMethod.GET)
    public void deleteBankMaster(@RequestParam("id") Long id) {
        bankService.deleteBankMaster(id);
    }

    @RequestMapping(value = "/getBankMaster", method = RequestMethod.GET)
    public ResponseEntity<?> getBankMaster() {
        try {
            List<BankMaster> bankNames = bankService.getBankMaster();
            return ResponseEntity.ok(bankNames);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/getBank", method = RequestMethod.GET)
    public ResponseEntity<?> getBankById(@RequestParam("id") String bankId) {
        try{
            return ResponseEntity.ok(bankService.getBankMasterById(Long.parseLong(bankId)));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}



