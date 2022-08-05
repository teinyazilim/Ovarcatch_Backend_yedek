package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.dto.*;
import com.tein.overcatchbackend.domain.model.*;
import com.tein.overcatchbackend.enums.AddressType;
import com.tein.overcatchbackend.enums.ModuleTypeEnum;
import com.tein.overcatchbackend.mapper.InvoiceDetailMapper;

import com.tein.overcatchbackend.mapper.InvoiceMapper;
import com.tein.overcatchbackend.mapper.InvoiceViewMapper;
import com.tein.overcatchbackend.property.FileStorageProperties;
import com.tein.overcatchbackend.repository.*;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
//@Transactional
public class InvoiceService {

    private final ClientRepository clientRepository;
    private final BankRepository bankRepository;
    private final InvoiceMapper invoiceMapper;
    private final InvoiceDetailMapper invoiceDetailMapper;
    private final InvoiceViewMapper invoiceViewMapper;
    private final InvoiceRepository invoiceRepository;
    private final IncomeExpenseRepository incomeExpenseRepository;
    private final InvoiceDetailRepository invoiceDetailRepository;
    private final ModuleTypeRepository moduleTypeRepository;
    private final MailService mailService;
    private final TaskService taskService;
    private final ModelMapper modelMapper;
    private final CurrentUserService currentUserService;
    private final FileStorageService fileStorageService;
    private final Path fileStorageLocation;
    private final DocumentService documentService;
    private final InvoiceTypeRepository invoiceTypeRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, FileStorageService fileStorageService,
                          InvoiceMapper invoiceMapper, FileStorageProperties fileStorageProperties,
                          ClientRepository clientRepository, InvoiceDetailMapper invoiceDetailMapper,
                          InvoiceViewMapper invoiceViewMapper, InvoiceDetailRepository invoiceDetailRepository,
                          ModuleTypeRepository moduleTypeRepository, ModelMapper modelMapper,MailService mailService,
                          TaskService taskService,CurrentUserService currentUserService,BankRepository bankRepository,
                          IncomeExpenseRepository incomeExpenseRepository, DocumentService documentService,
                          InvoiceTypeRepository invoiceTypeRepository) {
        this.invoiceRepository = invoiceRepository;
        this.fileStorageService = fileStorageService;
        this.invoiceMapper = invoiceMapper;
        this.clientRepository = clientRepository;
        this.bankRepository = bankRepository;
        this.invoiceDetailMapper = invoiceDetailMapper;
        this.invoiceViewMapper = invoiceViewMapper;
        this.invoiceDetailRepository = invoiceDetailRepository;
        this.moduleTypeRepository = moduleTypeRepository;
        this.modelMapper = modelMapper;
        this.mailService = mailService;
        this.taskService = taskService;
        this.incomeExpenseRepository=incomeExpenseRepository;
        this.currentUserService = currentUserService;
        this.documentService = documentService;
        this.invoiceTypeRepository = invoiceTypeRepository;
        this.fileStorageLocation =  Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
    }
    public ResponseEntity<?> invoiceSettings(InvoiceSettingsDTO invoiceSettingsDTO, MultipartFile file) {
        Client client = clientRepository.findById(invoiceSettingsDTO.getClientId()).get();
        client.setSelectedInvoiceType(invoiceSettingsDTO.getSelectedInvoiceType());
        client.setWeb(invoiceSettingsDTO.getWeb());
        client.setClientFileName(invoiceSettingsDTO.getFileName());
        client.setIsMailSend(invoiceSettingsDTO.getIsMailSend());

        InvoiceType invoiceSettingType = invoiceTypeRepository.findByClientIdInvoiceType(client.getId(),invoiceSettingsDTO.getInvoiceType());
        if(invoiceSettingType==null){
            invoiceSettingType = new InvoiceType();
        }
        invoiceSettingType.setClientId(client.getId());
        invoiceSettingType.setSelectedInvoiceType(invoiceSettingsDTO.getSelectedInvoiceType());
        invoiceSettingType.setIsMailSend(invoiceSettingsDTO.getIsMailSend());
        invoiceSettingType.setInvoiceType(invoiceSettingsDTO.getInvoiceType());
        invoiceSettingType.setIsActive(true);

        if (file != null) {
            if(!file.getOriginalFilename().equals(client.getClientFileName())){
                try {
                    String fileName = fileStorageService.storeFile(file, invoiceSettingsDTO.getFileName(), client.getClientFolder());
                } catch (Exception e) {
                    return ResponseEntity.badRequest().body(e.getMessage());
                }
            }
        }
        if (invoiceSettingsDTO.getIsInvoiceNumber()) {
            client.setInvoiceNumber(invoiceSettingsDTO.getInvoiceNumber());
            client.setIsInvoiceNumber(true);
            invoiceSettingType.setInvoiceNumber(invoiceSettingsDTO.getInvoiceNumber());
            invoiceSettingType.setIsInvoiceNumber(true);
        } else {
            client.setIsInvoiceNumber(false);
            invoiceSettingType.setIsInvoiceNumber(false);
        }
        try {
            InvoiceType invoiceSettingType1 = invoiceTypeRepository.findByClientIdInvoiceType(client.getId(),invoiceSettingsDTO.getInvoiceType());
            client.setInvoice_typeId(invoiceSettingType1);
            invoiceTypeRepository.save(invoiceSettingType);
            clientRepository.save(client);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Başarılı bir şekildi Kaydedildi.");
    }

    public List<InvoiceType> invoiceSettingsType(Long clientId){
        List<InvoiceType> invoiceType = invoiceTypeRepository.findByClientId(clientId);
        return invoiceType;
    }

    public InvoiceSettingsDTO invoiceSettings(Long clientId, String invoiceType) {
        Client client = clientRepository.findById(clientId).get();
        if(invoiceType == null || invoiceType == ""){
            invoiceType = "INVOICE";
        }
        InvoiceType invoiceType1 =  invoiceTypeRepository.findByClientIdInvoiceType(clientId,invoiceType);
        Integer invoiceNumber = 1;

        if(invoiceType1 != null){
            invoiceNumber = invoiceType1.getInvoiceNumber();
        }

        InvoiceSettingsDTO settingsDTO = InvoiceSettingsDTO.builder()
                .invoiceNumber(client.getInvoiceNumber())
                .clientId(client.getId())
                .isInvoiceNumber(client.getIsInvoiceNumber())
                .invoiceNumber(invoiceNumber)
                .web(client.getWeb())
                .filePath(client.getClientFolder())
                .selectedInvoiceType(client.getSelectedInvoiceType())
                .invoiceType(invoiceType)
                .fileName(client.getClientFileName()).build();
        return settingsDTO;
    }


    public void invoiceSave(InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceMapper.toEntity(invoiceDTO);
        Client client = clientRepository.findById(invoice.getClient().getId()).get();
        Bank bank = bankRepository.findById(invoice.getBank().getId()).get();
        InvoiceType invoiceType = invoiceTypeRepository.findByClientIdInvoiceType(client.getId(),invoiceDTO.getInvoiceType().toString());
        invoice.setIsActive(true);
        invoice.setUpdateState(0);
        invoice.setDeleteState(0);
        invoice.setSelectedInvoiceType(client.getSelectedInvoiceType());
        List<InvoiceDetail> invoiceDetails = invoiceDetailMapper.toEntity(invoiceDTO.getInvoiceDetails());
          invoice.setBank(bank);
        Invoice invoice1 = invoiceRepository.save(invoice);
        for (InvoiceDetail s : invoiceDetails) {
            s.setInvoice(invoice1);
        }
        try {
            if(invoice1.getId()!=null){
                invoiceDetailRepository.saveAll(invoiceDetails);
                if(invoiceType.getIsInvoiceNumber()==null || invoiceType.getIsInvoiceNumber()){
                    invoiceType.setInvoiceNumber(invoiceType.getInvoiceNumber()==null ? 1 : invoiceType.getInvoiceNumber() + 1);
                    client.setInvoiceNumber(client.getInvoiceNumber()==null ? 1: client.getInvoiceNumber() + 1);
                    clientRepository.save(client);
                    invoiceTypeRepository.save(invoiceType);                }
            }

            String invoicePdfName = documentService.createInvoicePdf(invoiceDTO);
            Resource resourcePdf = fileStorageService.loadFileAsResource(invoicePdfName, invoiceDTO.getClient().getClientFolder());

            //mailService.sendMessageWithAttachment(invoiceDTO.getBuyerEmail(), invoiceDTO.getClientEmail() ,"New Invoice " , invoiceDTO.getClient().getClientName(), resourcePdf);

            //Eğer Kullanıcnın user_language kısmı null ise default olarak Ingilizce olan template gönderiyorum ...
            if(invoiceDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage() == null){
                invoiceDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUserInfo().setUserlanguage("en");
            }

            //Email gönderme işlemi ingilizce template
            if(invoiceDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage().equals("en")){
                mailService.sendMessageWithAttachment(invoiceDTO.getBuyerEmail(), invoiceDTO.getClientEmail() ,"New Invoice " , invoiceDTO.getClient().getClientName(), resourcePdf);
            }
            //Email gönderme işlemi Türkçe template
            else if (invoiceDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage().equals("tr")){
                mailService.sendMessageWithAttachmentTR(invoiceDTO.getBuyerEmail(), invoiceDTO.getClientEmail() ,"Yeni Fatura " , invoiceDTO.getClient().getClientName(), resourcePdf);
            }
            documentService.deleteFileFromDisk(invoiceDTO.getClient().getClientFolder(), invoicePdfName);
            //return  invoiceDTO;


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ResponseEntity invoiceUpdate(InvoiceDTO invoiceDTO) {
        Invoice invoice2 = invoiceRepository.findByInvoiceId(invoiceDTO.getId());
        if(isMyInvoice(invoiceDTO.getId())){
            if(isMyInvoice(invoiceDTO.getId())){
                if (invoice2.getUpdateState() == 2 && invoice2.getDeleteState() == 0 && invoiceDTO.getClient().getId().equals(invoice2.getClient().getId())) {
                    if (invoice2.getInvoiceCode().equals(invoiceDTO.getInvoiceCode())) {
                        Invoice invoice = invoiceMapper.toEntity(invoiceDTO);
                        invoice.setUpdateState(0);
                        invoice.setIsActive(true);
                        Invoice invoice1 = invoiceRepository.save(invoice);
                        List<InvoiceDetail> invoiceDetails = invoiceDetailMapper.toEntity(invoiceDTO.getInvoiceDetails());
                        for (InvoiceDetail s : invoiceDetails) {
                            s.setInvoice(invoice1);
                        }
                        invoiceDetailRepository.saveAll(invoiceDetails);
                        return ResponseEntity.ok("Success");
                    } else {
                        return ResponseEntity.badRequest().body("Permission Denied.");
                    }
                } else {
                    return ResponseEntity.badRequest().body("Permission Denied.");
                }
            }else {
                return ResponseEntity.badRequest().body("Permission Denied.");
            }
        }else {
            return ResponseEntity.badRequest().body("Permission Denied.");
        }


    }

    public List<InvoiceViewDTO> getAllInvoiceByClient(Long clientId) {
        List<InvoiceViewDTO> test = invoiceViewMapper.toDto(invoiceRepository.findAllByClientId(clientId));
        return test;
    }

    public ResponseEntity<?> invoiceUpdateTask(Long invoiceId) {

            Invoice invoice = invoiceRepository.findById(invoiceId).get();
            if (invoice != null) {
                if ((invoice.getUpdateState() == 0 || invoice.getUpdateState() == 3)  && invoice.getDeleteState()==0) {
                    ModuleType moduleType = moduleTypeRepository.findByModuleTypeEnum1(ModuleTypeEnum.INVOICE_UPDATE.toString());
                    Tasks tasks = taskService.addTask(invoice.getClient(), moduleType);
                    invoice.getTasks().add(tasks);
                    invoice.setUpdateState(1);
                    invoiceRepository.save(invoice);
                } else {
                    return ResponseEntity.badRequest().body("Already Exist.");
                }

            } else {
                return ResponseEntity.badRequest().body("Invoice not found");
            }
            return ResponseEntity.ok("Success");

    }

    public ResponseEntity<?> invoiceDeleteTask(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId).get();
        if (invoice != null) {
            if ((invoice.getDeleteState() == 0 || invoice.getUpdateState() == 3)  && invoice.getUpdateState()==0) {
            ModuleType moduleType = moduleTypeRepository.findByModuleTypeEnum1(ModuleTypeEnum.INVOICE_DELETE.toString());
            Tasks tasks = taskService.addTask(invoice.getClient(), moduleType);
            invoice.getTasks().add(tasks);
            invoice.setDeleteState(1);
            invoiceRepository.save(invoice);
            } else {
                return ResponseEntity.badRequest().body("Already Exist.");
            }
        } else {
            return ResponseEntity.badRequest().body("Invoice not found");
        }
        return ResponseEntity.ok("Success");
    }

    public InvoiceViewDTO invoiceTaskDetail(Long taskId) {
        Invoice invoice = invoiceRepository.findByTaskId(taskId);
        InvoiceViewDTO invoiceViewDTO = invoiceViewMapper.toDto(invoice);
        return invoiceViewDTO;
    }


    public ResponseEntity<?> invoiceUpdateGet(Long invoiceId) {
        if(isMyInvoice(invoiceId)){
            Invoice invoice = invoiceRepository.findById(invoiceId).get();
            InvoiceViewDTO invoiceViewDTO = invoiceViewMapper.toDto(invoice);
            if (invoice.getUpdateState()==2){

                return ResponseEntity.ok(invoiceViewDTO);
            }else {
                return ResponseEntity.badRequest().body("Permission Denied");
            }
        }else{
            return ResponseEntity.badRequest().body("Not Found.");
        }
    }

    public void deleteInvoiceById(Long invoiceId) {

        invoiceRepository.deleteInvoiceById(invoiceId);
    }

    public ResponseEntity<?> getInvoiceDetailByInvoiceId(Long invoiceId) {
        if(isMyInvoice(invoiceId)){
            return ResponseEntity.ok(invoiceDetailMapper.toDto(invoiceDetailRepository.findAllByInvoiceId(invoiceId)));
        }else{
            return ResponseEntity.badRequest().body("Not Found.");
        }

    }

    public ResponseEntity<?> getInvoiceForCopy (Long invoiceId) throws Exception{
        return ResponseEntity.ok(invoiceRepository.findByInvoiceId(invoiceId));
    }

    public String getInvoiceNumber (Long clientId, String invoiceType){
        Client client = clientRepository.findById(clientId).get();
        InvoiceType invoiceType1 = invoiceTypeRepository.findByClientIdInvoiceType(clientId , invoiceType);
        String invoiceCode = "";
        if (invoiceType1.getIsInvoiceNumber()) {
            invoiceCode = invoiceType1.getInvoiceNumber().toString();
        } else {
            LocalDate localDate = LocalDate.now();
            LocalDate returnvalue = localDate.plusDays(1);
            if(invoiceType.equals("INVOICE")){
                invoiceType = "0";
            }
            else if( invoiceType.equals("CREDITNOTE")){
                invoiceType = "1";
            }
            else if( invoiceType.equals("PROFORMA")){
                invoiceType = "2";
            }
            else if( invoiceType.equals("DELIVERYNOTE")){
                invoiceType = "3";
            }
            else{
                invoiceType = "4";
            }
            int len = invoiceRepository.findByClientIdAndDateNow(clientId, localDate.toString(), returnvalue.toString(), Integer.parseInt(invoiceType)).size();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String formattedString = localDate.format(formatter);
            if (len == 0) {
                invoiceCode = formattedString;
            } else {
                invoiceCode = formattedString + "-" + len;
            }
        }
        return invoiceCode;
    }
    public InvoiceDTO newInvoiceGet(Long clientId, String invoiceType, String invoiceDate) {
        Client client = clientRepository.findById(clientId).get();
        InvoiceType invoiceType1 = invoiceTypeRepository.findByClientIdInvoiceType(clientId, invoiceType);
        String invoiceCode = "";
        if (invoiceType1.getIsInvoiceNumber()) {
            if(invoiceType1 != null){
                invoiceCode = invoiceType1.getInvoiceNumber().toString();
            }else{
                invoiceCode = "1";
            }
        } else {
            //LocalDate localDate = LocalDate.now();
            LocalDate localDate = LocalDate.parse(invoiceDate);
            LocalDate returnvalue = localDate.plusDays(1);
            if(invoiceType.equals("INVOICE")){
                invoiceType = "0";
            }
            else if( invoiceType.equals("CREDITNOTE")){
                invoiceType = "1";
            }
            else if( invoiceType.equals("PROFORMA")){
                invoiceType = "2";
            }
            else if( invoiceType.equals("DELIVERYNOTE")){
                invoiceType = "3";
            }
            else{
                invoiceType = "4";
            }
            int len = invoiceRepository.findByClientIdAndDateNow(clientId, localDate.toString(), returnvalue.toString(),Integer.parseInt(invoiceType)).size();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String formattedString = localDate.format(formatter);
            if (len == 0) {
                invoiceCode = formattedString;
            } else {
                invoiceCode = formattedString + "-" + len;
            }
        }
        String email = "";
        String phone = "";
        String clientName = "";
        if (client.getCompany() != null) {
            email = client.getCompany().getEmail();
            phone = client.getCompany().getPhoneNumber();
            clientName = client.getCompany().getName();

        } else {
            email = client.getFounderOwner().getEmail();
            phone = client.getFounderOwner().getPhoneNumber();
            clientName = client.getFounderOwner().getTradeAsName() != null ? client.getFounderOwner().getTradeAsName() : client.getFounderOwner().getName() + " " + client.getFounderOwner().getSurname();
        }
        String address = "";
        for (Address list : client.getAddressList()) {
            if (list.getAddressType() == AddressType.OFFICE) {
                address = list.getNumber() + " " + list.getStreet() + " " + list.getCounty() + " " + list.getPostcode();
            }
        }
        InvoiceDTO invoiceDTO = InvoiceDTO.builder()
                .invoiceCode(invoiceCode)
                .clientAddress(address)
                .clientEmail(email)
                .clientName(clientName)
                .clientPhone(phone).build();

        return invoiceDTO;
    }
//
//    public String getMailInvoice(InvoiceDTO invoiceDto) {
//        try {
//            String location = fileStorageService.loadFileForInvoice(invoiceDto);
//            System.out.println(location);
//            return mailService.sendMessageWithAttachment(invoiceDto.getBuyerEmail(),"you have an invoice by " + invoiceDto.getBuyerName(), invoiceDto.getClient().getClientFileName(), location);
//        } catch (Exception e) {
//            return e.getMessage();
//        }
//    }

    public Set<Tasks> objeToSet(Tasks tasks) {
        Set<Tasks> hash = new HashSet<>();
        hash.add(tasks);
        return hash;
    }

    public Boolean isMyInvoice(Long invoiceId){
        User user=currentUserService.getCurrentUser();
        Set<CustomerClient> customerClients= user.getCustomer().getCustomerClients();
        Invoice invoice2 = invoiceRepository.findByInvoiceId(invoiceId);
        CustomerClient customerClient=customerClients.stream().filter(x->x.getClient().getId()==invoice2.getClient().getId()).findFirst().get();
        if(customerClient!=null){
            return true;
        }else{
            return false;
        }
    }

    public Page<Invoice> getFilterInvoice(ForFilterInvoice filterInvoice){

        List<String> invoiceTypes = new ArrayList<String>();

        String buyerName = filterInvoice.getBuyerName();
        String search = filterInvoice.getSearch();
        String currency = filterInvoice.getCurrency();
        String invoiceType  = filterInvoice.getInvoiceType()==null  ? "0,1,2,3,4" : filterInvoice.getInvoiceType();
        invoiceTypes = new ArrayList<String>(Arrays.asList(invoiceType.split(",")));
        //        InvoiceType invoiceType = filterInvoice.getInvoiceType();
        LocalDate invoiceDate = filterInvoice.getInvoiceDate();
        LocalDate invoiceEndDate = filterInvoice.getInvoiceEndDate();
        Long client_id = filterInvoice.getClient_id();
        Integer size = filterInvoice.getSize();
        Integer page = filterInvoice.getPage();
        Pageable pageAble = PageRequest.of(page,size);
        try {
            Page<Invoice> filteredInvoice = invoiceRepository.findAllByFilter(buyerName,currency, invoiceDate, invoiceEndDate,client_id,invoiceTypes,search, pageAble);
            return filteredInvoice;
        }catch (Exception e){
        }
        return  null;
    }
}