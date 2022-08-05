package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.CashInvoiceDTO;
import com.tein.overcatchbackend.domain.dto.ExpensesTypeDTO;
import com.tein.overcatchbackend.domain.dto.exception.AppException;
import com.tein.overcatchbackend.domain.model.*;
import com.tein.overcatchbackend.mapper.CashInvoiceMapper;
import com.tein.overcatchbackend.mapper.ExpensesTypeMapper;
import com.tein.overcatchbackend.repository.ClientRepository;
import com.tein.overcatchbackend.service.CashInvoiceService;
import com.tein.overcatchbackend.service.CurrentUserService;
import com.tein.overcatchbackend.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hpsf.Blob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("api/cashInvoice")
@RequiredArgsConstructor
public class CashInvoiceResource {
    private final CashInvoiceService cashInvoiceService;
    private final ClientRepository clientRepository;
    private final ExpensesTypeMapper expensesTypeMapper;
    private final CurrentUserService currentUserService;
    private final CashInvoiceMapper cashInvoiceMapper;

    @RequestMapping(value = "/saveCash", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<?> saveCashInvoice(@RequestParam (required = false, name = "id") String id,
                                             @RequestParam(required = false, name = "file") MultipartFile file,
                                             @RequestParam(required = false, name = "clientId") String clientId,
                                             @RequestParam(required = false, name = "cashPrice") String cashPrice,
                                             @RequestParam(required = false, name = "cashCurrencyOfPayment") String cashCurrencyOfPayment,
                                             @RequestParam(required = false, name = "cashInvoiceTypeId") String cashInvoiceTypeId,
                                             @RequestParam(required = false, name = "cashInvoiceDate") String cashInvoiceDate,
                                             @RequestParam(required = false, name = "isActive") String isActive,
                                             @RequestParam(required = false, name = "cashInvoiceType") String cashInvoiceType
    ) {

        if(cashInvoiceDate.substring(cashInvoiceDate.indexOf("-")).length()==8){
            String day = "";
            String mounth = "";
            String year = "";
            for(int i= 0; i<=1;i++){
                day = day + cashInvoiceDate.charAt(i);
            }
            for(int i=3; i<=4; i++){
                mounth = mounth + cashInvoiceDate.charAt(i);
            }
            for(int i=6; i<=9; i++){
                year = year + cashInvoiceDate.charAt(i);
            }
            cashInvoiceDate = year + "-" + mounth + "-" + day;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(cashInvoiceDate, formatter);
        Client client = clientRepository.findByClientId(Long.parseLong(clientId)).orElseThrow(() -> new AppException("Company not found"));
        String fileName = client.getId() + "-" +
                cashInvoiceTypeId + "-" + DateUtil.toString(LocalDateTime.now()) + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));

        CashInvoiceDTO cashInvoiceDTO = new CashInvoiceDTO();
        if(id.equals("undefined")){
            id=null;
        }
        if(id!=null ){
            cashInvoiceDTO.setId(Long.parseLong(id));
        }
        cashInvoiceDTO.setClient(client);
        cashInvoiceDTO.setCashInvoiceTypeId(Long.parseLong(cashInvoiceTypeId));
        cashInvoiceDTO.setCashInvoiceType(cashInvoiceType);
        cashInvoiceDTO.setInvoiceDate(date);
        cashInvoiceDTO.setCurrencyOfPayment(cashCurrencyOfPayment);
        cashInvoiceDTO.setPrice(Float.parseFloat(cashPrice));
        cashInvoiceDTO.setFileName(fileName);
        cashInvoiceDTO.setFilePath(client.getClientFolder());
        cashInvoiceDTO.setIsActive(Boolean.parseBoolean(isActive));
        cashInvoiceDTO.setUpdateState(0);
        cashInvoiceDTO.setDeleteState(0);
        return cashInvoiceService.saveCashInvoice(cashInvoiceDTO, file);
    }

    @RequestMapping(value = "/allByClientId", method = RequestMethod.GET)
    public Page<CashInvoiceDTO> getAllByClientId(@RequestParam("clientId") String clientId
            , @RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "5") int size) {
        return cashInvoiceService.getAllCashInvoiceByClient(Long.valueOf(clientId), page, size);
    }
//    @RequestMapping (value = "/allByClientWithoutPage", method = RequestMethod.GET)
//    public List<CashInvoice> getAllByClientWithoutPage(@RequestParam("clientId") String clientId){
//        return cashInvoiceService.getAllForExcelByClientId(Long.parseLong(clientId));
//    }
    @RequestMapping(value = "/getAllForExcelByClientId", method = RequestMethod.POST)
    public List<CashInvoice> getAllForExcelByClientId(@RequestBody ForFilterExpensesExcell forFilter) {
        return cashInvoiceService.getAllForExcelByClientId(forFilter);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<?> deleteCashInvoiceById(@RequestParam("cashId") String cashId) {
        try {
            cashInvoiceService.deleteCashInvoiceById(Long.valueOf(cashId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

    @RequestMapping(value = "/createDeleteRequest", method = RequestMethod.GET)
    public ResponseEntity<?> createDeleteRequest(@RequestParam("cashId") String cashId) {
        try {
            cashInvoiceService.createDeleteRequest(Long.valueOf(cashId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

    @RequestMapping(value = "/createUpdateRequest", method = RequestMethod.GET)
    public ResponseEntity<?> createUpdateRequest(@RequestParam("cashId") String cashId) {
        try {
            cashInvoiceService.createUpdateRequest(Long.valueOf(cashId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

    @RequestMapping(value = "getByTaskId", method = RequestMethod.GET)
    public CashInvoiceDTO getByTaskId(@RequestParam Long taskId){
        CashInvoiceDTO cashInvoiceDTO = null;
        try {
            cashInvoiceDTO = cashInvoiceService.getByTaskId(taskId);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return cashInvoiceDTO;
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public ResponseEntity<?> getExpensesByFilter(@RequestBody ForFilterExpenses forFilter) {
        try {
            Page<CashInvoice> invoices = cashInvoiceService.getFilterExpenses(forFilter);

            List<CashInvoiceDTO> asd = cashInvoiceMapper.toDto(invoices.toList());

            Page<CashInvoiceDTO> responseClients = new PageImpl<>(asd , invoices.getPageable(),invoices.getTotalElements());
            return ResponseEntity.ok(responseClients);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/getExpensesType", method = RequestMethod.GET)
    public ResponseEntity<?> getExpensesType() {
        try {
            List<ExpensesType> invoices = cashInvoiceService.getExpensesType();
            return ResponseEntity.ok(invoices);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/getExpensesTypeById", method = RequestMethod.GET)
    public ResponseEntity<?> getExpensesTypeById(@RequestParam Long id) {
        try {
            ExpensesType invoices = cashInvoiceService.getExpensesTypeById(id);
            return ResponseEntity.ok(invoices);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/saveExpensesType", method = RequestMethod.POST)
    public void saveExpensesType(@RequestBody ExpensesType expensesType) {
        try {
            cashInvoiceService.saveExpensesType(expensesType);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ExpensesType updateCompany(@RequestBody ExpensesTypeDTO expensesTypeDTO) {
        ExpensesType expensesType = expensesTypeMapper.toEntity(expensesTypeDTO);
        return cashInvoiceService.updateExpensesType(expensesType);
    }

    @RequestMapping(value = "/deleteExpenseType", method = RequestMethod.GET)
    public ResponseEntity<?> deleteExpenseTypeById(@RequestParam("expenseTypeId") String expenseTypeId) {
        try {
            cashInvoiceService.deleteExpensesType(Long.valueOf(expenseTypeId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/deletedeneme/id")
    public void removeOne(@PathVariable("id") Long id) {
        cashInvoiceService.deleteExpenseType(id);
    }

    @RequestMapping(value = "/controlExpensesForDelete", method = RequestMethod.GET)
    public Integer controlBankForDelete(@RequestParam("expensesType_id") String expensesType_id) {
        return cashInvoiceService.controlExpensesForDelete(Long.parseLong(expensesType_id));
    }
}