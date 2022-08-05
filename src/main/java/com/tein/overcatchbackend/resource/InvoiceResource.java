package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.*;
import com.tein.overcatchbackend.domain.dto.exception.AppException;
import com.tein.overcatchbackend.domain.model.*;
import com.tein.overcatchbackend.enums.DocumentType;
import com.tein.overcatchbackend.mapper.HelpTypeMapper;
import com.tein.overcatchbackend.mapper.InvoiceMapper;
import com.tein.overcatchbackend.repository.ClientRepository;
import com.tein.overcatchbackend.service.ExistCompanyExportService;
import com.tein.overcatchbackend.service.HelpTypeService;
import com.tein.overcatchbackend.service.InvoiceService;
import com.tein.overcatchbackend.service.MailService;
import com.tein.overcatchbackend.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.tein.overcatchbackend.domain.dto.InvoiceDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("api/invoice")
@RequiredArgsConstructor
public class InvoiceResource {
    private final InvoiceService invoiceService;
    private final MailService mailService;
    private final ClientRepository clientRepository;
    private final InvoiceMapper invoiceMapper;

    @RequestMapping(value = "/invoiceSettings", method = RequestMethod.POST)
    public ResponseEntity<?> invoiceSettings(@RequestParam(required = false,name = "file") MultipartFile file,
                                             @RequestParam(required = false, name = "clientId") String clientId,
                                             @RequestParam(required = false, name = "isInvoiceNumber") String isInvoiceNumber,
                                             @RequestParam(required = false, name = "invoiceNumber") String invoiceNumber,
                                             @RequestParam(required = false, name = "selectedInvoiceType") Integer selectedInvoiceType,
                                             @RequestParam(required = false, name = "web") String web,
                                             @RequestParam(required = false, name="isMailSend") Boolean isMailSend,
                                             @RequestParam (required = false, name = "invoiceType") String invoiceType
                                             ) {
        String fileName="";
        InvoiceSettingsDTO invoiceSettingsDTO=new InvoiceSettingsDTO();
        Client client = clientRepository.findByClientId(Long.parseLong(clientId)).orElseThrow(() -> new AppException("Company not found"));
        String a= client.getClientFileName();
        String b= file.getOriginalFilename();
        if(!a.equals(b)){

            try{
                if (invoiceNumber !=null ){
                    fileName = clientId + "-" +
                            invoiceNumber + "-" + DateUtil.toString(LocalDateTime.now()) + file.getOriginalFilename();
                }
                else{
                    fileName = clientId + "-" +
                            client.getAgreementType().toString() + "-" +
                            DateUtil.toString(LocalDateTime.now()) + file.getOriginalFilename();
                }
            }catch (Exception e){

            }
        }else{
            fileName=file.getOriginalFilename();
        }
        if (invoiceNumber !=null ){
            invoiceSettingsDTO.setInvoiceNumber(Integer.parseInt(invoiceNumber));
        }
        invoiceSettingsDTO.setIsInvoiceNumber(isInvoiceNumber.equals("true")?Boolean.TRUE:Boolean.FALSE);
        invoiceSettingsDTO.setClientId(Long.parseLong(clientId));
        invoiceSettingsDTO.setSelectedInvoiceType(selectedInvoiceType);
        invoiceSettingsDTO.setFileName(fileName);
        invoiceSettingsDTO.setFilePath(client.getClientFolder());
        invoiceSettingsDTO.setIsMailSend(isMailSend);
        invoiceSettingsDTO.setInvoiceType(invoiceType);
        if(web.equals("undefined")){
            invoiceSettingsDTO.setWeb("");
        }else{
            invoiceSettingsDTO.setWeb(web);
        }
        return invoiceService.invoiceSettings(invoiceSettingsDTO,file);
    }

    @RequestMapping(value = "/getInvoiceSettings", method = RequestMethod.GET)
    public InvoiceSettingsDTO getInvoiceSettings(@RequestParam("clientId") String clientId,
                                                 @RequestParam("invoiceType") String invoiceType) {
        return invoiceService.invoiceSettings(Long.valueOf(clientId), invoiceType);
    }

    @RequestMapping(value="/invoiceSettingsType", method = RequestMethod.GET)
    public List<InvoiceType> invoiceSettingsType (@RequestParam("clientId") String clientId){
        List<InvoiceType> invoiceTypes = invoiceService.invoiceSettingsType(Long.valueOf(clientId));
        return invoiceTypes;
    }

    @RequestMapping(value = "/addInvoice", method = RequestMethod.POST)
    public void invoiceSettings(@RequestBody InvoiceDTO invoiceDTO) {
       invoiceService.invoiceSave(invoiceDTO);
    }

    @RequestMapping(value = "/allbyclientid", method = RequestMethod.GET)
    public List<InvoiceViewDTO> getAllByClientId(@RequestParam("clientId") String clientId) {
        return invoiceService.getAllInvoiceByClient(Long.valueOf(clientId));
    }
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<?> deleteBuyerById(@RequestParam("invoiceId") String invoiceId) {
        try {
            invoiceService.deleteInvoiceById(Long.valueOf(invoiceId));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

    @RequestMapping(value = "/detailbyinvoiceId", method = RequestMethod.GET)
    public ResponseEntity<?> getInvoiceByInvoiceId(@RequestParam("invoiceId") String invoiceId) {
        return invoiceService.getInvoiceDetailByInvoiceId(Long.valueOf(invoiceId));
    }

    @RequestMapping(value="/getInvoice", method = RequestMethod.GET)
    public ResponseEntity<?> getInvoiceForCopy (@RequestParam("invoiceId")String invoiceId){
        try {
            return invoiceService.getInvoiceForCopy(Long.valueOf(invoiceId));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/getInvoiceNumber", method = RequestMethod.GET)
    public String getInvoiceNumber(@RequestParam("clientId") String clientId,
                                   @RequestParam("invoiceType") String invoiceType){
        return invoiceService.getInvoiceNumber(Long.valueOf(clientId), invoiceType);
    }

    @RequestMapping(value = "/newInvoiceGet", method = RequestMethod.GET)
    public InvoiceDTO getNewInvoice(@RequestParam("clientId") String clientId,
                                    @RequestParam("invoiceType") String invoiceType,
                                    @RequestParam(required = false, name = "invoiceDate") String invoiceDate) {
        return invoiceService.newInvoiceGet(Long.valueOf(clientId), invoiceType, invoiceDate);
    }
//    @RequestMapping(value = "/invoiceMail", method = RequestMethod.GET)
//    public ResponseEntity<?> getMail(@RequestParam("invoiceId") String invoiceId) {
//        try {
//            return ResponseEntity.ok(invoiceService.getMailInvoice(Long.valueOf(invoiceId)));
//        }catch (Exception ex){
//            return ResponseEntity.badRequest().body(ex.getMessage());
//        }
//    }

    @RequestMapping(value = "/updateInvoiceRequest", method = RequestMethod.GET)
    public ResponseEntity<?> getInvoiceUpdateTask(@RequestParam("invoiceId") String invoiceId) {
        return invoiceService.invoiceUpdateTask(Long.valueOf(invoiceId));
    }

    @RequestMapping(value = "/updateInvoice", method = RequestMethod.POST)
    public ResponseEntity invoiceUpdate(@RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.invoiceUpdate(invoiceDTO);
    }

    @RequestMapping(value = "/deleteInvoiceRequest", method = RequestMethod.GET)
    public ResponseEntity<?> getInvoiceDeleteTask(@RequestParam("invoiceId") String invoiceId) {
        return invoiceService.invoiceDeleteTask(Long.valueOf(invoiceId));
    }

    @RequestMapping(value = "/getInvoiceTaskDetail", method = RequestMethod.GET)
    public InvoiceViewDTO getInvoiceTaskDetail(@RequestParam("taskId") String taskId) {
        return invoiceService.invoiceTaskDetail(Long.valueOf(taskId));
    }

    @RequestMapping(value = "/getInvoiceUpdate", method = RequestMethod.GET)
    public ResponseEntity<?> getInvoiceUpdate(@RequestParam("invoiceId") String invoiceId) {
        return invoiceService.invoiceUpdateGet(Long.valueOf(invoiceId));
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public Page<Invoice> getInvoicesByFilter(@RequestBody ForFilterInvoice filterInvoice) {
        Page<Invoice> invoices = invoiceService.getFilterInvoice(filterInvoice);
        return invoices;
    }
}
