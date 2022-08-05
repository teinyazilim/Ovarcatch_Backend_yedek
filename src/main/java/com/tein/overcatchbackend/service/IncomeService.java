package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.dto.IncomeDTO;
import com.tein.overcatchbackend.domain.model.*;
import com.tein.overcatchbackend.enums.ModuleTypeEnum;
import com.tein.overcatchbackend.mapper.IncomeMapper;
import com.tein.overcatchbackend.property.FileStorageProperties;
import com.tein.overcatchbackend.repository.*;
import lombok.AllArgsConstructor;
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

@Service
@AllArgsConstructor
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final IncomeTypeRepository incomeTypeRepository;
    private final IncomeMapper incomeMapper;
    private final FileStorageService fileStorageService;
    private final ModuleTypeRepository moduleTypeRepository;
    private final TaskService taskService;
    private final Path fileStorageLocation;


    @Autowired
    public IncomeService(IncomeRepository incomeRepository, FileStorageService fileStorageService, IncomeMapper incomeMapper, FileStorageProperties fileStorageProperties, IncomeTypeRepository incomeTypeRepository, TaskService taskService, ModuleTypeRepository moduleTypeRepository) {
        this.incomeRepository = incomeRepository;
        this.fileStorageService = fileStorageService;
        this.incomeMapper = incomeMapper;
        this.incomeTypeRepository = incomeTypeRepository;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        this.taskService = taskService;
        this.moduleTypeRepository = moduleTypeRepository;
    }

    public ResponseEntity<?> saveIncome(IncomeDTO incomeDTO, MultipartFile file) {

        if(file != null){
            try{
                String fileName = fileStorageService.storeFile(file,incomeDTO.getFileName(), incomeDTO.getFilePath());
            }catch(Exception e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        try{
            Income income = incomeMapper.toEntity(incomeDTO);
            incomeRepository.save(income);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

    public Page<IncomeDTO> getAllIncomeByClient(Long clientId, int page, int size){
        Pageable pageAble = PageRequest.of(page, size);
        Page<Income> incomeList = incomeRepository.findAllByClientId(clientId,pageAble);
        List<IncomeDTO> incomeListDTO = incomeMapper.toDto(incomeList.toList());
        Page<IncomeDTO> responseIncomes = new PageImpl<>(incomeListDTO, incomeList.getPageable(), incomeList.getTotalElements());

        return responseIncomes;
    }

    public void createDeleteRequest(Long incomeId) throws Exception{
        Income income = incomeRepository.findById(incomeId).get();
        ModuleType moduleType = moduleTypeRepository.findByModuleTypeEnum1(ModuleTypeEnum.INCOME_DELETE.toString());
        Tasks tasks = taskService.addTask(income.getClient(),moduleType);
        income.setTask(tasks);
        income.setDeleteState(1);
        incomeRepository.save(income);
    }

    public void createUpdateRequest(Long incomeId) throws Exception{
        Income income = incomeRepository.findById(incomeId).get();
        ModuleType moduleType = moduleTypeRepository.findByModuleTypeEnum1(ModuleTypeEnum.INCOME_UPDATE.toString());
        Tasks tasks = taskService.addTask(income.getClient(),moduleType);
        income.setTask(tasks);
        income.setUpdateState(1);
        incomeRepository.save(income);
    }

    public IncomeDTO getByTaskId(Long taskId) throws Exception{
        Income income = incomeRepository.findByTaskId(taskId);
        return incomeMapper.toDto(income);
    }

    public void deleteIncomeById(Long incomeId) throws Exception{
        Income income = incomeRepository.findById(incomeId).get();
        incomeRepository.deleteIncomeById(incomeId);
        Path targetLocation = this.fileStorageLocation.resolve(income.getClient().getClientFolder() + "\\" + income.getFileName());
        Files.delete(targetLocation);
    }

    public List<Income> getIncomeForExs(Long clientId){
        return incomeRepository.findAllByClientIdWithout(clientId);
    }

    public List<Income> getFilterIncome(ForFilterIncome filterIncome) throws Exception{
        String search = filterIncome.getSearch();
        String incomeCurrency = filterIncome.getCurrency();
        String incomeType = filterIncome.getIncomeType();
        LocalDate incomeDate = filterIncome.getIncomeDate();
        LocalDate incomeEndDate = filterIncome.getIncomeEndDate();
        Long client_id = filterIncome.getClientId();
        List<Income> incomeList = incomeRepository.findByFilterForExcel(incomeCurrency, incomeDate, incomeEndDate, client_id, incomeType, search);
        return incomeList;
    }

    public Page<Income> getFilterIncomeWithPage (ForFilterIncome filterIncome){
        String search = filterIncome.getSearch();
        String incomeCurrency = filterIncome.getCurrency();
        String incomeType = filterIncome.getIncomeType();
        LocalDate incomeDate = filterIncome.getIncomeDate();
        LocalDate incomeEndDate = filterIncome.getIncomeEndDate();
        Long client_id = filterIncome.getClientId();
        List<Income> incomeList = incomeRepository.findByFilterForExcel(incomeCurrency, incomeDate, incomeEndDate, client_id, incomeType, search);
        Integer size = filterIncome.getSize();
        Integer page = filterIncome.getPage();
        Pageable pageAble = PageRequest.of(page, size);
        Page<Income> filteredIncome = incomeRepository.findAllByFilter(incomeCurrency, incomeDate, incomeEndDate, client_id, incomeType, search, pageAble);
        return filteredIncome;
    }

    public List<IncomeType> getIncomesType() throws Exception {

        List<IncomeType> incomeTypes = incomeTypeRepository.findAllActive();
        return incomeTypes;
    }

    public IncomeType getIncomeTypeById(Long id) throws Exception {

        IncomeType incomeType = incomeTypeRepository.findById(id).get();
        return incomeType;
    }

    public void saveIncomesType(IncomeType incomesType) {
        IncomeType incomeType = new IncomeType();
        incomeType.setIncomesType(incomesType.getIncomesType());
        incomeType.setIsActive(true);
        incomeTypeRepository.save(incomeType);
    }

    public IncomeType updateIncomeType(IncomeType incomesType) {
        incomesType.setIsActive(true);
        IncomeType incomeType = incomeTypeRepository.save(incomesType);
        return incomeType;
    }

    public ResponseEntity<?> deleteIncomesType(Long id) {
        incomeTypeRepository.deleteIncomesType(id);
        return ResponseEntity.ok("Success");
    }

    public void deleteIncomeType(Long id) {
        incomeTypeRepository.deleteIncomesType(id);
    }

    public Integer controlIncomeForDelete(Long incomesType_id) {
        int i = incomeTypeRepository.controlIncomesForDelete(incomesType_id);
        return i;
    }
}
