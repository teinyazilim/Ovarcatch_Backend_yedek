package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.CashInvoiceDTO;
import com.tein.overcatchbackend.domain.dto.IncomeDTO;
import com.tein.overcatchbackend.domain.dto.IncomeTypeDTO;
import com.tein.overcatchbackend.domain.dto.exception.AppException;
import com.tein.overcatchbackend.domain.model.*;
import com.tein.overcatchbackend.mapper.IncomeMapper;
import com.tein.overcatchbackend.mapper.IncomeTypeMapper;
import com.tein.overcatchbackend.repository.ClientRepository;
import com.tein.overcatchbackend.service.IncomeService;
import com.tein.overcatchbackend.util.DateUtil;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("api/income")
@RequiredArgsConstructor
public class IncomeResource {

    private final IncomeService incomeService;
    private final ClientRepository clientRepository;
    private final IncomeTypeMapper incomeTypeMapper;
    private final IncomeMapper incomeMapper;

    @RequestMapping(value = "/saveIncome", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<?> saveIncome(@RequestParam(required = false, name = "id") String id,
                                             @RequestParam(required = false, name = "file") MultipartFile file,
                                             @RequestParam(required = false, name = "clientId") String clientId,
                                             @RequestParam(required = false, name = "price") String price,
                                             @RequestParam(required = false, name = "currencyOfPayment") String currencyOfPayment,
                                             @RequestParam(required = false, name = "incomeTypeId") String incomeTypeId,
                                             @RequestParam(required = false, name = "incomeDate") String incomeDate,
                                             @RequestParam(required = false, name = "isActive") String isActive,
                                             @RequestParam(required = false, name = "incomeType") String incomeType
    ) {
        System.out.println(incomeDate);
        if(incomeDate.substring(incomeDate.indexOf("-")).length()==8){
            String day = "";
            String mounth = "";
            String year = "";
            for(int i= 0; i<=1;i++){
                day = day + incomeDate.charAt(i);
            }
            for(int i=3; i<=4; i++){
                mounth = mounth + incomeDate.charAt(i);
            }
            for(int i=6; i<=9; i++){
                year = year + incomeDate.charAt(i);
            }
            incomeDate = year + "-" + mounth + "-" + day;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(incomeDate, formatter);
        Client client = clientRepository.findByClientId(Long.parseLong(clientId)).orElseThrow(() -> new AppException("Company not found"));
        String fileName = client.getId() + "-" +
                incomeTypeId + "-" + DateUtil.toString(LocalDateTime.now()) + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));

        IncomeDTO incomeDTO = new IncomeDTO();
        if(id.equals("undefined")){
            id=null;
        }
        if(id!=null ){
            incomeDTO.setId(Long.parseLong(id));
        }
        incomeDTO.setClient(client);
        incomeDTO.setIncomeTypeId(Long.parseLong(incomeTypeId));
        incomeDTO.setIncomeType(incomeType);
        incomeDTO.setIncomeDate(date);
        incomeDTO.setCurrencyOfPayment(currencyOfPayment);
        incomeDTO.setPrice(Float.parseFloat(price));
        incomeDTO.setFileName(fileName);
        incomeDTO.setFilePath(client.getClientFolder());
        incomeDTO.setIsActive(Boolean.parseBoolean(isActive));
        incomeDTO.setUpdateState(0);
        incomeDTO.setDeleteState(0);
        return incomeService.saveIncome(incomeDTO, file);
    }

    @RequestMapping(value = "/allByClientId", method = RequestMethod.GET)
    public Page<IncomeDTO> getAllByClientId(@RequestParam("clientId") String clientId
            , @RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "5") int size) {
        return incomeService.getAllIncomeByClient(Long.valueOf(clientId), page, size);
    }

//    @RequestMapping(value = "/getAllForExcelByClientId", method = RequestMethod.POST)
//    public List<Income> getAllForExcelByClientId(@RequestBody ForFilterExpensesExcell forFilter) {
//        return incomeService.getAllForExcelByClientId(forFilter);
//    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<?> deleteCashInvoiceById(@RequestParam("incomeId") String incomeId) {
        try {
            incomeService.deleteIncomeById(Long.valueOf(incomeId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

    @RequestMapping(value = "/createDeleteRequest", method = RequestMethod.GET)
    public ResponseEntity<?> createDeleteRequest(@RequestParam("incomeId") String incomeId) {
        try {
            incomeService.createDeleteRequest(Long.valueOf(incomeId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

    @RequestMapping(value = "/createUpdateRequest", method = RequestMethod.GET)
    public ResponseEntity<?> createUpdateRequest(@RequestParam("incomeId") String incomeId) {
        try {
            incomeService.createUpdateRequest(Long.valueOf(incomeId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

    @RequestMapping(value = "getByTaskId", method = RequestMethod.GET)
    public IncomeDTO getByTaskId(@RequestParam Long taskId){
        IncomeDTO incomeDTO = null;
        try {
            incomeDTO = incomeService.getByTaskId(taskId);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return incomeDTO;
    }


    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public ResponseEntity<?> getExpensesByFilter(@RequestBody ForFilterIncome forFilter) {
        try {
            Page<Income> incomes = incomeService.getFilterIncomeWithPage(forFilter);

            List<IncomeDTO> asd = incomeMapper.toDto(incomes.toList());

            Page<IncomeDTO> responseClients = new PageImpl<>(asd , incomes.getPageable(),incomes.getTotalElements());

            return ResponseEntity.ok(responseClients);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/getIncomesType", method = RequestMethod.GET)
    public ResponseEntity<?> getIncomesType() {
        try {
            List<IncomeType> incomes = incomeService.getIncomesType();
            return ResponseEntity.ok(incomes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @RequestMapping(value = "/getIncomeTypeById", method = RequestMethod.GET)
    public ResponseEntity<?> getIncomeTypeById(@RequestParam Long id) {
        try {
            IncomeType incomes = incomeService.getIncomeTypeById(id);
            return ResponseEntity.ok(incomes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/saveIncomesType", method = RequestMethod.POST)
    public void saveExpensesType(@RequestBody IncomeType incomesType) {
        try {
            incomeService.saveIncomesType(incomesType);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public IncomeType updateIncome(@RequestBody IncomeTypeDTO incomeTypeDTO) {
        IncomeType incomeType = incomeTypeMapper.toEntity(incomeTypeDTO);
        return incomeService.updateIncomeType(incomeType);
    }

    @RequestMapping(value = "/deleteIncomeType", method = RequestMethod.GET)
    public ResponseEntity<?> deleteIncomeTypeById(@RequestParam("incomeTypeId") String incomeTypeId) {
        try {
            incomeService.deleteIncomesType(Long.valueOf(incomeTypeId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/deleteIncome/id")
    public void removeOne(@PathVariable("id") Long id) {
        incomeService.deleteIncomeType(id);
    }

    @RequestMapping(value = "/controlIncomeForDelete", method = RequestMethod.GET)
    public Integer controlBankForDelete(@RequestParam("incomesType_id") String incomesType_id) {
        return incomeService.controlIncomeForDelete(Long.parseLong(incomesType_id));
    }
}
