package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.InvoiceViewDTO;
import com.tein.overcatchbackend.domain.model.ForFilterInvoice;
import com.tein.overcatchbackend.domain.model.ForIncomeExpense;
import com.tein.overcatchbackend.domain.model.Invoice;
import com.tein.overcatchbackend.service.IncomeExpenseService;
import com.tein.overcatchbackend.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/incomeExpense")
@RequiredArgsConstructor
public class IncomeExpenseResource {
    private final IncomeExpenseService incomeExpenseService;
    private final InvoiceService invoiceService;

    @RequestMapping(value = "/allByClientId", method = RequestMethod.GET)
    public List<InvoiceViewDTO> getAllByClientId(@RequestParam("clientId") String clientId) {
        return invoiceService.getAllInvoiceByClient(Long.valueOf(clientId));
    }

    @RequestMapping(value = "/getListIncomeExpense", method = RequestMethod.POST)
    public ResponseEntity<?> getMail(@RequestParam("clientId") Long clientId) {
        try {
            return ResponseEntity.ok(incomeExpenseService.getList(clientId));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public ResponseEntity<?> getByFilter(@RequestBody ForIncomeExpense filter) {
        try {
            return ResponseEntity.ok(incomeExpenseService.getListByFilter(filter));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @RequestMapping(value = "/getInvoiceForIncomeExpenseListFilter", method = RequestMethod.POST)
    public ResponseEntity<?> getInvoiceForIncomeExpenseListFilter(@RequestParam("clientId") Long clientId){
        try {
            return ResponseEntity.ok(incomeExpenseService.getInvoiceForIncomeExpenseListFilter(clientId));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @RequestMapping(value = "/getExpensesForIncomeExpenseListFilter", method = RequestMethod.POST)
    public ResponseEntity<?> getExpensesForIncomeExpenseListFilter(@RequestParam("clientId") Long clientId){
        try {
            return ResponseEntity.ok(incomeExpenseService.getExpensesForIncomeExpenseListFilter(clientId));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @RequestMapping(value = "/getBankForIncomeExpenseListFilter", method = RequestMethod.POST)
    public ResponseEntity<?> getBankForIncomeExpenseListFilter(@RequestParam("clientId") Long clientId){
        try {
            return ResponseEntity.ok(incomeExpenseService.getBankForIncomeExpenseListFilter(clientId));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
