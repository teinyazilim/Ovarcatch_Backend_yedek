package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.dto.BankTransactionDTO;
import com.tein.overcatchbackend.domain.dto.CashInvoiceDTO;
import com.tein.overcatchbackend.domain.dto.InvoiceDTO;
import com.tein.overcatchbackend.domain.dto.InvoiceViewDTO;
import com.tein.overcatchbackend.domain.model.*;
import com.tein.overcatchbackend.mapper.InvoiceMapper;
import com.tein.overcatchbackend.mapper.InvoiceViewMapper;
import com.tein.overcatchbackend.repository.ExpensesManagerRepository;
import com.tein.overcatchbackend.repository.IncomeExpenseRepository;
import com.tein.overcatchbackend.repository.InvoiceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hpsf.Decimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class IncomeExpenseService {
    private final IncomeExpenseRepository incomeExpenseRepository;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceViewMapper invoiceViewMapper;
    private final InvoiceService invoiceService;
    private final CashInvoiceService cashInvoiceService;
    private final BankTransactionService bankTransactionService;
    private final ExpensesManagerRepository expensesManagerRepository;

    public List<InvoiceViewDTO> getAllInvoiceByClient(Long clientId) {
        List<InvoiceViewDTO> test = invoiceViewMapper.toDto(invoiceRepository.findAllByClientId(clientId));
        return test;
    }

    public ResponseEntity<?> getList(Long clientId){
        try{
            List<InvoiceViewDTO> invoiceList = invoiceService.getAllInvoiceByClient(clientId);
            List<CashInvoice> cashInvoiceDTOList = cashInvoiceService.getCashForExpenses(clientId);
            List<BankTransactionDTO> bankTransactionDTOList = bankTransactionService.getBankTransactionsByClientId(clientId);

            List <Object> listForInExPage = new ArrayList<>();
            List<Object> incomeList = new ArrayList<>();
            List<Object> expenseList = new ArrayList<>();

            for(InvoiceViewDTO invoiceList1: invoiceList){
                listForInExPage.add(invoiceList1);
            };
            for(CashInvoice cash: cashInvoiceDTOList){
                listForInExPage.add(cash);
            };
            for(BankTransactionDTO bank: bankTransactionDTOList){
                listForInExPage.add(bank);
            };
            return ResponseEntity.ok(listForInExPage);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public List<BankTransaction> getForReportByFilter(ForIncomeExpense forIncomeExpense){
        Long clientId = forIncomeExpense.getClientId();
        LocalDate startDate = forIncomeExpense.getStartDate();
        LocalDate endDate = forIncomeExpense.getEndDate();
        List<BankTransaction> bank = incomeExpenseRepository.getForReportFilterBank(clientId,startDate,endDate);
        return  bank;
    }
    public List<CashInvoice> getForReportByFilterCash(ForIncomeExpense forIncomeExpense){
        List<ExpensesType> expensesTypes = expensesManagerRepository.findAllActive();
        List<String> typeExpenses = new ArrayList<String>();
        Long clientId = forIncomeExpense.getClientId();
        LocalDate startDate = forIncomeExpense.getStartDate();
        LocalDate endDate = forIncomeExpense.getEndDate();
//        String _type = forIncomeExpense.getType().substring(forIncomeExpense.getType().indexOf(".")+1);
//        for(ExpensesType exType : expensesTypes){
//            if(_type.equals(exType.getExpensesType())){
//                List<CashInvoice> list = incomeExpenseRepository.getForReportByFilterCash1(clientId,startDate,endDate,_type);
//                return list;
//            }
//        }
        if(StringUtils.isEmpty(forIncomeExpense.getExpenseTypes()) || forIncomeExpense.getExpenseTypes().equals("[object Object]")){
            for (ExpensesType moduleType1: expensesTypes) {
                typeExpenses.add(moduleType1.getExpensesType());
            }
        }else{
            typeExpenses = new ArrayList<String>(Arrays.asList(forIncomeExpense.getExpenseTypes().split(",")));
        }
        List<CashInvoice> cashInvoices = incomeExpenseRepository.getForReportByFilterCash(clientId,startDate,endDate,typeExpenses);
        return cashInvoices;
    }
    public List<Invoice> getForReportByFilterInvoice(ForIncomeExpense forIncomeExpense){
        Long clientId = forIncomeExpense.getClientId();
        LocalDate startDate = forIncomeExpense.getStartDate();
        LocalDate endDate = forIncomeExpense.getEndDate();
//        String invoiceType = forIncomeExpense.getType();
        List<String> typeInvoices = new ArrayList<String>();
        List<Integer> typeInvoices1 = new ArrayList<Integer>();
        if(StringUtils.isEmpty(forIncomeExpense.getInvoiceTypes())){
            typeInvoices1.add(0);
            typeInvoices1.add(1);
            typeInvoices1.add(2);
            typeInvoices1.add(3);
            typeInvoices1.add(4);
        }else{
            typeInvoices = new ArrayList<String>(Arrays.asList(forIncomeExpense.getInvoiceTypes().split(",")));
            for(String invType : typeInvoices){
                if(invType.equals("INVOICE")){
//                    typeInvoices.remove(invType);
                    typeInvoices1.add(0);
                }
                else if( invType.equals("CREDITNOTE")){
//                    typeInvoices.remove(invType);
                    typeInvoices1.add(1);
                }
                else if( invType.equals("PROFORMA")){
//                    typeInvoices.remove(invType);
                    typeInvoices1.add(2);
                }
                else if( invType.equals("DELIVERYNOTE")){
//                    typeInvoices.remove(invType);
                    typeInvoices1.add(3);
                }
                else if (invType.equals("SELFCERTIFICATE")){
//                    typeInvoices.remove(invType);
                    typeInvoices1.add(4);
                }
//                List <Invoice> invoiceList = incomeExpenseRepository.getForReportByFilter1(clientId,startDate,endDate);
//                return invoiceList;
            }
        }

//        if(invoiceType.equals("INVOICE")){
//            invoiceType = "0";
//        }
//        else if( invoiceType.equals("CREDITNOTE")){
//            invoiceType = "1";
//        }
//        else if( invoiceType.equals("PROFORMA")){
//            invoiceType = "2";
//        }
//        else if( invoiceType.equals("DELIVERYNOTE")){
//            invoiceType = "3";
//        }
//        else if (invoiceType.equals("SELFCERTIFICATE")){
//            invoiceType = "4";
//        }
//        else {
//            List <Invoice> invoiceList = incomeExpenseRepository.getForReportByFilter1(clientId,startDate,endDate);
//            return invoiceList;
//        }
        List<Invoice> invoices = incomeExpenseRepository.getForReportByFilter(clientId,startDate,endDate,typeInvoices1 );
        return invoices;
    }
    public ResponseEntity<?> getListByFilter(ForIncomeExpense filter){
        try{
            ArrayList<Object> listForInExPage = new ArrayList<>();
            if(filter.getExpenseTypes() != null && !filter.getExpenseTypes().equals("")) {
                System.out.println(filter.getExpenseTypes().substring(0, 3));
                if (filter.getExpenseTypes().substring(0,3).equals("[ob")) {
                    System.out.println(filter.getExpenseTypes().substring(0, 3));
                    filter.setExpenseTypes(null);
                }
            }
//            if(filter.getType() == null || filter.getType().equals("undefined")){
//                filter.setType("type");
//            }
//            String _type = filter.getType();

            if (!StringUtils.isEmpty(filter.getExpenseTypes())){
                List<CashInvoice> cashInvoiceDTOList = getForReportByFilterCash(filter);
                for(CashInvoice cash: cashInvoiceDTOList){
                    listForInExPage.add(cash);
                }
                return ResponseEntity.ok(listForInExPage);
            }
            if (!StringUtils.isEmpty(filter.getInvoiceTypes())){
                List<String> typ = new ArrayList<String>(Arrays.asList(filter.getInvoiceTypes().split(",")));
                for(String exp : typ){
                    if(exp.equals("EXPENSES") || exp.equals("expenses")){
                        List<CashInvoice> cashInvoiceDTOList = getForReportByFilterCash(filter);
                        for(CashInvoice cash: cashInvoiceDTOList){
                            listForInExPage.add(cash);
                        }
//                        return ResponseEntity.ok(listForInExPage);
                    }else if(exp.equals("bank") || exp.equals("BANK")){
                        List<BankTransaction> bankTransactionDTOList = getForReportByFilter(filter);
                        for(BankTransaction bank: bankTransactionDTOList){
                            listForInExPage.add(bank);
                        }
//                        return ResponseEntity.ok(listForInExPage);
                    }
                }
                List<Invoice> invoiceList = getForReportByFilterInvoice(filter);
                for(Invoice invoiceList1: invoiceList){
                    listForInExPage.add(invoiceList1);
                }
                return ResponseEntity.ok(listForInExPage);
            }
//            if(_type.equals("INVOICE") || _type.equals("CREDITNOTE") || _type.equals("PROFORMA") || _type.equals("DELIVERYNOTE") || _type.equals("SELFCERTIFICATE")){
//                List<Invoice> invoiceList = getForReportByFilterInvoice(filter);
//                for(Invoice invoiceList1: invoiceList){
//                    listForInExPage.add(invoiceList1);
//                }
//                return ResponseEntity.ok(listForInExPage);
//            }else if (_type.equals("EXPENSES") || _type.equals("expenses")){
//
//                List<CashInvoice> cashInvoiceDTOList = getForReportByFilterCash(filter);
//                for(CashInvoice cash: cashInvoiceDTOList){
//                    listForInExPage.add(cash);
//                }
//                return ResponseEntity.ok(listForInExPage);
//            }else if(_type.equals("bank")){
//                List<BankTransaction> bankTransactionDTOList = getForReportByFilter(filter);
//                for(BankTransaction bank: bankTransactionDTOList){
//                    listForInExPage.add(bank);
//                }
//                return ResponseEntity.ok(listForInExPage);
//            }

                List<Invoice> invoiceList = getForReportByFilterInvoice(filter);
                List<CashInvoice> cashInvoiceDTOList = getForReportByFilterCash(filter);
                List<BankTransaction> bankTransactionDTOList = getForReportByFilter(filter);
                for(Invoice invoiceList1: invoiceList){
                    listForInExPage.add(invoiceList1);
                }
                for(CashInvoice cash: cashInvoiceDTOList){
                    listForInExPage.add(cash);
                }
                for(BankTransaction bank: bankTransactionDTOList){
                    listForInExPage.add(bank);
                }
                return ResponseEntity.ok(listForInExPage);

        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public ResponseEntity<?> getInvoiceForIncomeExpenseListFilter(Long clientId){
        try{
            List<InvoiceViewDTO> invoiceList = invoiceService.getAllInvoiceByClient(clientId);
            return ResponseEntity.ok(invoiceList);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public ResponseEntity<?> getExpensesForIncomeExpenseListFilter(Long clientId){
        try{
            List<CashInvoice> expenseList = cashInvoiceService.getCashForExpenses(clientId);
            return ResponseEntity.ok(expenseList);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public ResponseEntity<?> getBankForIncomeExpenseListFilter(Long clientId){
        try{
            List<BankTransactionDTO> bankList = bankTransactionService.getBankTransactionsByClientId(clientId);
            return ResponseEntity.ok(bankList);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
