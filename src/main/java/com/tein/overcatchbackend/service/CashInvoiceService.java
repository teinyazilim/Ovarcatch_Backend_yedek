package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.dto.CashInvoiceDTO;
import com.tein.overcatchbackend.domain.model.*;
import com.tein.overcatchbackend.enums.ModuleTypeEnum;
import com.tein.overcatchbackend.mapper.CashInvoiceMapper;
import com.tein.overcatchbackend.property.FileStorageProperties;
import com.tein.overcatchbackend.repository.CashInvoiceRepository;
import com.tein.overcatchbackend.repository.ClientRepository;
import com.tein.overcatchbackend.repository.ExpensesManagerRepository;
import com.tein.overcatchbackend.repository.ModuleTypeRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.hpsf.Blob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CashInvoiceService {

    private final CashInvoiceRepository cashInvoiceRepository;
    private final ExpensesManagerRepository expensesManagerRepository;
    private final CashInvoiceMapper cashInvoiceMapper;
    private final FileStorageService fileStorageService;
    private final Path fileStorageLocation;
    private ClientRepository clientRepository;
    private final TaskService taskService;
    private final ModuleTypeRepository moduleTypeRepository;

    @Autowired
    public CashInvoiceService(CashInvoiceRepository cashInvoiceRepository, FileStorageService fileStorageService, CashInvoiceMapper cashInvoiceMapper, FileStorageProperties fileStorageProperties, ExpensesManagerRepository expensesManagerRepository, TaskService taskService, ModuleTypeRepository moduleTypeRepository) {
        this.cashInvoiceRepository = cashInvoiceRepository;
        this.fileStorageService = fileStorageService;
        this.cashInvoiceMapper = cashInvoiceMapper;
        this.expensesManagerRepository = expensesManagerRepository;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        this.taskService = taskService;
        this.moduleTypeRepository = moduleTypeRepository;
    }

    public ResponseEntity<?> saveCashInvoice(CashInvoiceDTO cashInvoiceDTO, MultipartFile file) {
        if (file != null) {
            try {
                String fileName = fileStorageService.storeFile(file, cashInvoiceDTO.getFileName(), cashInvoiceDTO.getFilePath());
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        try {
            CashInvoice cashInvoice = cashInvoiceMapper.toEntity(cashInvoiceDTO);
            cashInvoiceRepository.save(cashInvoice);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }
    public ResponseEntity<?> updateCashInvoice (CashInvoiceDTO cashInvoiceDTO) throws Exception{
        try{
            CashInvoice cashInvoice = cashInvoiceMapper.toEntity(cashInvoiceDTO);
            cashInvoiceRepository.save(cashInvoice);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }
    public Page<CashInvoiceDTO> getAllCashInvoiceByClient(Long clientId, int page, int size) {

        Pageable pageAble = PageRequest.of(page, size);
        Page<CashInvoice> cashInvoiceList = cashInvoiceRepository.findAllByClientId(clientId, pageAble);
        List<CashInvoiceDTO> cashInvoiceListDTO = cashInvoiceMapper.toDto(cashInvoiceList.toList());
        Page<CashInvoiceDTO> responseClients = new PageImpl<>(cashInvoiceListDTO, cashInvoiceList.getPageable(), cashInvoiceList.getTotalElements());

        return responseClients;
    }

    public void createDeleteRequest(Long cashId) throws Exception {
        CashInvoice cashInvoice = cashInvoiceRepository.findById(cashId).get();
        ModuleType moduleType=moduleTypeRepository.findByModuleTypeEnum1(ModuleTypeEnum.EXPENSE_DELETE.toString());
        Tasks tasks = taskService.addTask(cashInvoice.getClient(), moduleType);
        cashInvoice.setTask(tasks);
        cashInvoice.setDeleteState(1);
        cashInvoiceRepository.save(cashInvoice);
    }
    public void createUpdateRequest(Long cashId) throws Exception {
        CashInvoice cashInvoice = cashInvoiceRepository.findById(cashId).get();
        ModuleType moduleType=moduleTypeRepository.findByModuleTypeEnum1(ModuleTypeEnum.EXPENSE_UPDATE.toString());
        Tasks tasks = taskService.addTask(cashInvoice.getClient(), moduleType);
        cashInvoice.setTask(tasks);
        cashInvoice.setUpdateState(1);
        cashInvoiceRepository.save(cashInvoice);
    }

    public CashInvoiceDTO getByTaskId(Long taskId) throws Exception{
        CashInvoice cashInvoice = cashInvoiceRepository.findByTaskId(taskId);
        return cashInvoiceMapper.toDto(cashInvoice);
    }

    public void deleteCashInvoiceById(Long cashId) throws Exception {
        CashInvoice cashInvoice = cashInvoiceRepository.findById(cashId).get();
        //veritabında aktifliği 0 yapma
        cashInvoiceRepository.deleteCashInvoiceById(cashId);
        //bilgisayardan silme işlemi
        Path targetLocation = this.fileStorageLocation.resolve(cashInvoice.getClient().getClientFolder() + "\\" + cashInvoice.getFileName()); //for windows
        Files.delete(targetLocation);
    }

    public List<CashInvoice> getCashForExpenses(Long clientId){
        return cashInvoiceRepository.findAllByClientId1(clientId);
    }

    public Page<CashInvoice> getFilterExpenses(ForFilterExpenses filterInvoice) throws Exception {
        String search = filterInvoice.getSearch();
        String invoiceCurrency = filterInvoice.getInvoiceCurrency();
        String invoiceType = filterInvoice.getInvoiceType();
        LocalDate invoiceDate = filterInvoice.getInvoiceDate();
        LocalDate invoiceEndDate = filterInvoice.getInvoiceEndDate();
        Long client_id = filterInvoice.getClientId();
        Integer size = filterInvoice.getSize();
        Integer page = filterInvoice.getPage();

        Pageable pageAble = PageRequest.of(page, size);
        Page<CashInvoice> filteredInvoice = cashInvoiceRepository.findAllByFilter(invoiceCurrency, invoiceDate, invoiceEndDate, client_id, invoiceType, search, pageAble);
        return filteredInvoice;

    }

    public List<CashInvoice> getAllForExcelByClientId(ForFilterExpensesExcell forFilter){
        String search = forFilter.getSearch();
        String invoiceCurrency = forFilter.getInvoiceCurrency();
        String invoiceType = forFilter.getInvoiceType();
        LocalDate invoiceDate = forFilter.getInvoiceDate();
        LocalDate invoiceEndDate = forFilter.getInvoiceEndDate();
        Long client_id = forFilter.getClientId();
//        Integer size = forFilter.getSize();
//        Integer page = forFilter.getPage();
        List<CashInvoice> cashInvoiceList = cashInvoiceRepository.findByFilterForExcell(invoiceCurrency,invoiceDate,invoiceEndDate,client_id,invoiceType,search);
        return cashInvoiceList;
    }

    public List<ExpensesType> getExpensesType() throws Exception {

        List<ExpensesType> filteredInvoice = expensesManagerRepository.findAllActive();
        return filteredInvoice;
    }

    public ExpensesType getExpensesTypeById(Long id) throws Exception {

        ExpensesType filteredInvoice = expensesManagerRepository.findById(id).get();
        return filteredInvoice;
    }

    public void saveExpensesType(ExpensesType expensesType) {
        ExpensesType expensesType1 = new ExpensesType();
        expensesType1.setExpensesType(expensesType.getExpensesType());
        expensesType1.setIsActive(true);
        expensesManagerRepository.save(expensesType1);
    }

    public ExpensesType updateExpensesType(ExpensesType expensesType) {
        expensesType.setIsActive(true);
        ExpensesType expensesType1 = expensesManagerRepository.save(expensesType);
        return expensesType1;
    }

    public ResponseEntity<?> deleteExpensesType(Long id) {
        expensesManagerRepository.deleteExpensesType(id);
        return ResponseEntity.ok("Success");
    }

    public void deleteExpenseType(Long id) {

        expensesManagerRepository.deleteExpensesType(id);
    }

    public Integer controlExpensesForDelete(Long expensesType_id) {
        int i = expensesManagerRepository.controlExpensesForDelete(expensesType_id);

        return i;
    }
}
