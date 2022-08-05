package com.tein.overcatchbackend.service;


import com.tein.overcatchbackend.domain.ModuleTypesResponse;
import com.tein.overcatchbackend.domain.dto.*;
import com.tein.overcatchbackend.domain.model.*;
import com.tein.overcatchbackend.enums.ModuleTypeEnum;
import com.tein.overcatchbackend.enums.TaskConfirmEnum;
import com.tein.overcatchbackend.mapper.CashInvoiceMapper;
import com.tein.overcatchbackend.mapper.IncomeMapper;
import com.tein.overcatchbackend.mapper.TaskConfirmationMapper;

import com.tein.overcatchbackend.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TaskConfirmationService {
    private final TaskConfirmationRepository taskConfirmationRepository;
    private final TaskConfirmationMapper taskConfirmationMapper;
    private final TaskRepository taskRepository;
    private final CurrentUserService currentUserService;
    private final ClientRepository clientRepository;
    private final TaskModuleTypesRepository taskModuleTypesRepository;
    private final MailService mailService;
    private final InvoiceRepository invoiceRepository;
    private final LetterService letterService;
    private final FileStorageService fileStorageService;
    private final DocumentService documentService;
    private final CashInvoiceService cashInvoiceService;
    private final IncomeService incomeService;
    private final PersonelRepository personelRepository;
    private final CashInvoiceRepository cashInvoiceRepository;
    private final IncomeRepository incomeRepository;
    private final CashInvoiceMapper cashInvoiceMapper;
    private final IncomeMapper incomeMapper;

    public TaskConfirmation changeTaskConfirmation(TaskConfirmEnum taskConfirmType,Long taskId,String message) {

        TaskConfirmation taskConfirmation=new TaskConfirmation();
        taskConfirmation.setProcessDate(LocalDateTime.now());
        taskConfirmation.setTaskConfirm(taskConfirmType);
        taskConfirmation.setMessage(message);
        taskConfirmation.setPersonel(currentUserService.getCurrentUser().getPersonel());
        taskConfirmation.setTasks(taskRepository.findById(taskId).get());
        Tasks t=taskRepository.findById(taskId).get();
        if(t.getModuleType().getModuleTypeEnum()==ModuleTypeEnum.INVOICE_UPDATE){
            Invoice invoice= invoiceRepository.findByTaskId(taskId);
            if(taskConfirmType == TaskConfirmEnum.DONE){
                invoice.setUpdateState(2);
            }
            if(taskConfirmType == TaskConfirmEnum.REJECTED){
                invoice.setUpdateState(3);
            }
            invoiceRepository.save(invoice);
        }
        if(t.getModuleType().getModuleTypeEnum()==ModuleTypeEnum.INVOICE_DELETE){
            Invoice invoice= invoiceRepository.findByTaskId(taskId);
            if(taskConfirmType == TaskConfirmEnum.DONE){
                invoice.setDeleteState(2);
                invoiceRepository.deleteInvoiceById(invoice.getId());
            }
            if(taskConfirmType == TaskConfirmEnum.REJECTED){
                invoice.setDeleteState(3);

            }
            invoiceRepository.save(invoice);

        }
        String state;
        if(t.getModuleType().getModuleTypeEnum() == ModuleTypeEnum.COMPANY_CREATE){
            if(taskConfirmType == TaskConfirmEnum.DONE){
                state="3";
            }
            else if(taskConfirmType == TaskConfirmEnum.REJECTED){
                state="2";
            }
            else{
                state="1";
            }
            clientRepository.changeState(t.getClient().getId(),state);
        }
        if(taskConfirmType != TaskConfirmEnum.INPROGRESS)
        mailService.sendMailHtmlTaskConfirm(t.getClient().getFounderOwner()!=null ? t.getClient().getFounderOwner().getEmail():t.getClient().getCompany().getEmail(),t.getModuleType().getModuleTypeEnum().getName(),taskConfirmType.toString(),message);
        return taskConfirmationRepository.save(taskConfirmation);
    }

    public TaskConfirmation doneTaskConfirmation(TaskConfirmEnum taskConfirmType,Long taskId,String message) {

        LocalDateTime now = LocalDateTime.now();
        TaskConfirmation taskConfirmation=new TaskConfirmation();
        taskConfirmation.setProcessDate(now);
        taskConfirmation.setTaskConfirm(taskConfirmType);
        taskConfirmation.setMessage(message);
        //personel null kontrolü
        if (currentUserService.getCurrentUser().getPersonel() == null){
            Personel personel = new Personel();
            personel.setUser(currentUserService.getCurrentUser());
            taskConfirmation.setPersonel(personelRepository.save(personel));
        }else{
            taskConfirmation.setPersonel(currentUserService.getCurrentUser().getPersonel());
        }
        taskConfirmation.setTasks(taskRepository.findById(taskId).get());


        Tasks t=taskRepository.findById(taskId).get();
        taskConfirmation.setTaskCreatedTime(t.getCreatedDateTime());
        taskConfirmation.setTasks(t);
        if(t.getModuleType().getModuleTypeEnum()==ModuleTypeEnum.INVOICE_UPDATE){
            Invoice invoice= invoiceRepository.findByTaskId(taskId);
            if(taskConfirmType == TaskConfirmEnum.DONE){
                invoice.setUpdateState(2);
            }
            if(taskConfirmType == TaskConfirmEnum.REJECTED){
                invoice.setUpdateState(3);
            }
            invoiceRepository.save(invoice);
        }
        if(t.getModuleType().getModuleTypeEnum()==ModuleTypeEnum.INVOICE_DELETE){
            Invoice invoice= invoiceRepository.findByTaskId(taskId);
            if(taskConfirmType == TaskConfirmEnum.DONE){
                invoice.setDeleteState(2);
                invoiceRepository.deleteInvoiceById(invoice.getId());
            }
            if(taskConfirmType == TaskConfirmEnum.REJECTED){
                invoice.setDeleteState(3);
            }
            invoiceRepository.save(invoice);

        }
        else if(t.getModuleType().getModuleTypeEnum() == ModuleTypeEnum.LETTER_MODULE){

            try {
                LetterDTO letter = letterService.getLetterByTaskId(taskId);
                Resource resourceDoc = null;
                Resource resourcePdf = null;
                String pdfFileName = "";
                if (letter.getDocument() != null)
                    resourceDoc = fileStorageService.loadFileAsResource(letter.getDocument().getFileName(), letter.getClient().getClientFolder());

                if (taskConfirmType == TaskConfirmEnum.DONE){
                    pdfFileName = documentService.createpdf(letter);
                    resourcePdf = fileStorageService.loadFileAsResource(pdfFileName, letter.getClient().getClientFolder());
                }
                User currentUser = currentUserService.getCurrentUser();

                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String formatDate = now.format(format);

                String mailTo = letter.getClient().getCustomerClients().get(0).getCustomerInfo().getUserInfo().getEmail();
                if(letter.getClient().getFounderOwner() != null){
                    if(letter.getClient().getFounderOwner().getEmail() != null)
                        mailTo = letter.getClient().getFounderOwner().getEmail();
                }else if(letter.getClient().getCompany() != null){
                    if(letter.getClient().getCompany().getEmail() != null)
                        mailTo = letter.getClient().getCompany().getEmail();
                }

                //Eğer Kullanıcnın user_language kısmı null ise default olarak Ingilizce olan template gönderiyorum ...
                if(letter.getClient().getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage() == null){
                    letter.getClient().getCustomerClients().get(0).getCustomerInfo().getUserInfo().setUserlanguage("en");
                }

                if(letter.getClient().getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage().equals("tr")){
                    mailService.sendLetterRequestTR(mailTo,
                            currentUser.getEmail() ,
                            resourceDoc != null ? resourceDoc.getFile() : null,
                            resourcePdf != null ? resourcePdf.getFile() : null,
                            letter.getClient().getCustomerClients().get(0).getCustomerInfo().getUserInfo().getName() + " " + letter.getClient().getCustomerClients().get(0).getCustomerInfo().getUserInfo().getSurname(),
                            message,
                            currentUser.getName() + " " + currentUser.getSurname(),
                            formatDate, taskConfirmType, letter.getLetterType().getLetterTypeName());
                }
                else if (letter.getClient().getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage().equals("en")){
                    mailService.sendLetterRequest(mailTo,
                            currentUser.getEmail() ,
                            resourceDoc != null ? resourceDoc.getFile() : null,
                            resourcePdf != null ? resourcePdf.getFile() : null,
                            letter.getClient().getCustomerClients().get(0).getCustomerInfo().getUserInfo().getName() + " " + letter.getClient().getCustomerClients().get(0).getCustomerInfo().getUserInfo().getSurname(),
                            message,
                            currentUser.getName() + " " + currentUser.getSurname(),
                            formatDate, taskConfirmType, letter.getLetterType().getLetterTypeName());
                }

                documentService.deleteFileFromDisk(letter.getClient().getClientFolder(), pdfFileName);

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        else if(t.getModuleType().getModuleTypeEnum() == ModuleTypeEnum.EXPENSE_DELETE){
            Integer deleteState;
            if(taskConfirmType == TaskConfirmEnum.DONE){
                deleteState=3;
            }
            else if(taskConfirmType == TaskConfirmEnum.REJECTED){
                deleteState=2;
            }
            else{
                deleteState=1;
            }
            try {
                CashInvoiceDTO cashInvoiceDTO = cashInvoiceService.getByTaskId(taskId);
                User currentUser = currentUserService.getCurrentUser();
//                taskConfirmType
                cashInvoiceDTO.setDeleteState(deleteState);
                cashInvoiceRepository.save(cashInvoiceMapper.toEntity(cashInvoiceDTO));
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String formatDate = now.format(format);

                String mailTo = cashInvoiceDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getEmail();
                if(cashInvoiceDTO.getClient().getFounderOwner() != null){
                    if(cashInvoiceDTO.getClient().getFounderOwner().getEmail() != null)
                        mailTo = cashInvoiceDTO.getClient().getFounderOwner().getEmail();
                }else if(cashInvoiceDTO.getClient().getCompany() != null){
                    if(cashInvoiceDTO.getClient().getCompany().getEmail() != null)
                        mailTo = cashInvoiceDTO.getClient().getCompany().getEmail();
                }

                //Eğer Kullanıcnın user_language kısmı null ise default olarak Ingilizce olan template gönderiyorum ...
                if(cashInvoiceDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getUserlanguage() == null){
                    cashInvoiceDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().setUserlanguage("en");
                }

                if (cashInvoiceDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getUserlanguage().equals("tr")){
                    mailService.deleteCashInvoiceRequestTR(
                            mailTo,
                            currentUser.getEmail(),
                            cashInvoiceDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getName() + " " + cashInvoiceDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getSurname(),
                            message,
                            currentUser.getName() + " " + currentUser.getSurname(),
                            formatDate,
                            taskConfirmType
                    );
                }
                else if (cashInvoiceDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getUserlanguage().equals("en")){
                    mailService.deleteCashInvoiceRequest(
                            mailTo,
                            currentUser.getEmail(),
                            cashInvoiceDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getName() + " " + cashInvoiceDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getSurname(),
                            message,
                            currentUser.getName() + " " + currentUser.getSurname(),
                            formatDate,
                            taskConfirmType
                    );
                }

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        else if(t.getModuleType().getModuleTypeEnum() == ModuleTypeEnum.EXPENSE_UPDATE){
            Integer state;
            if(taskConfirmType == TaskConfirmEnum.DONE){
                state=3;
            }
            else if(taskConfirmType == TaskConfirmEnum.REJECTED){
                state=2;
            }
            else{
                state=1;
            }
            try {
                CashInvoiceDTO cashInvoiceDTO = cashInvoiceService.getByTaskId(taskId);
                User currentUser = currentUserService.getCurrentUser();
//                taskConfirmType
                cashInvoiceDTO.setUpdateState(state);
                cashInvoiceRepository.save(cashInvoiceMapper.toEntity(cashInvoiceDTO));
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String formatDate = now.format(format);
                String mailTo = cashInvoiceDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getEmail();
                if(cashInvoiceDTO.getClient().getFounderOwner() != null){
                    if(cashInvoiceDTO.getClient().getFounderOwner().getEmail() != null)
                        mailTo = cashInvoiceDTO.getClient().getFounderOwner().getEmail();
                }else if(cashInvoiceDTO.getClient().getCompany() != null){
                    if(cashInvoiceDTO.getClient().getCompany().getEmail() != null)
                        mailTo = cashInvoiceDTO.getClient().getCompany().getEmail();
                }
                //Eğer Kullanıcnın user_language kısmı null ise default olarak Ingilizce olan template gönderiyorum ...
                if(cashInvoiceDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getUserlanguage() == null){
                    cashInvoiceDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().setUserlanguage("en");
                }
                if (cashInvoiceDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getUserlanguage().equals("tr")){
                    mailService.deleteCashInvoiceRequestTR(
                            mailTo,
                            currentUser.getEmail(),
                            cashInvoiceDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getName() + " " + cashInvoiceDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getSurname(),
                            message,
                            currentUser.getName() + " " + currentUser.getSurname(),
                            formatDate,
                            taskConfirmType
                    );
                }
                else if (cashInvoiceDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getUserlanguage().equals("en")){
                    mailService.deleteCashInvoiceRequest(
                            mailTo,
                            currentUser.getEmail(),
                            cashInvoiceDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getName() + " " + cashInvoiceDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getSurname(),
                            message,
                            currentUser.getName() + " " + currentUser.getSurname(),
                            formatDate,
                            taskConfirmType
                    );
                }

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        else if(t.getModuleType().getModuleTypeEnum() == ModuleTypeEnum.INCOME_DELETE){
            Integer deleteState;
            if(taskConfirmType == TaskConfirmEnum.DONE){
                deleteState=3;
            }
            else if(taskConfirmType == TaskConfirmEnum.REJECTED){
                deleteState=2;
            }
            else{
                deleteState=1;
            }
            try {
                IncomeDTO incomeDTO = incomeService.getByTaskId(taskId);
                User currentUser = currentUserService.getCurrentUser();
//                taskConfirmType
                incomeDTO.setDeleteState(deleteState);
                incomeRepository.save(incomeMapper.toEntity(incomeDTO));
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String formatDate = now.format(format);

                String mailTo = incomeDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getEmail();
                if(incomeDTO.getClient().getFounderOwner() != null){
                    if(incomeDTO.getClient().getFounderOwner().getEmail() != null)
                        mailTo = incomeDTO.getClient().getFounderOwner().getEmail();
                }else if(incomeDTO.getClient().getCompany() != null){
                    if(incomeDTO.getClient().getCompany().getEmail() != null)
                        mailTo = incomeDTO.getClient().getCompany().getEmail();
                }

                //Eğer Kullanıcnın user_language kısmı null ise default olarak Ingilizce olan template gönderiyorum ...
                if(incomeDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getUserlanguage() == null){
                    incomeDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().setUserlanguage("en");
                }

                if (incomeDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getUserlanguage().equals("tr")){
                    mailService.deleteCashInvoiceRequestTR(
                            mailTo,
                            currentUser.getEmail(),
                            incomeDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getName() + " " + incomeDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getSurname(),
                            message,
                            currentUser.getName() + " " + currentUser.getSurname(),
                            formatDate,
                            taskConfirmType
                    );
                }
                else if (incomeDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getUserlanguage().equals("en")){
                    mailService.deleteCashInvoiceRequest(
                            mailTo,
                            currentUser.getEmail(),
                            incomeDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getName() + " " + incomeDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getSurname(),
                            message,
                            currentUser.getName() + " " + currentUser.getSurname(),
                            formatDate,
                            taskConfirmType
                    );
                }

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        else if(t.getModuleType().getModuleTypeEnum() == ModuleTypeEnum.INCOME_UPDATE){
            Integer state;
            if(taskConfirmType == TaskConfirmEnum.DONE){
                state=3;
            }
            else if(taskConfirmType == TaskConfirmEnum.REJECTED){
                state=2;
            }
            else{
                state=1;
            }
            try {
                IncomeDTO incomeDTO = incomeService.getByTaskId(taskId);
                User currentUser = currentUserService.getCurrentUser();
//                taskConfirmType
                incomeDTO.setUpdateState(state);
                incomeRepository.save(incomeMapper.toEntity(incomeDTO));
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String formatDate = now.format(format);
                String mailTo = incomeDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getEmail();
                if(incomeDTO.getClient().getFounderOwner() != null){
                    if(incomeDTO.getClient().getFounderOwner().getEmail() != null)
                        mailTo = incomeDTO.getClient().getFounderOwner().getEmail();
                }else if(incomeDTO.getClient().getCompany() != null){
                    if(incomeDTO.getClient().getCompany().getEmail() != null)
                        mailTo = incomeDTO.getClient().getCompany().getEmail();
                }
                //Eğer Kullanıcnın user_language kısmı null ise default olarak Ingilizce olan template gönderiyorum ...
                if(incomeDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getUserlanguage() == null){
                    incomeDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().setUserlanguage("en");
                }
                if (incomeDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getUserlanguage().equals("tr")){
                    mailService.deleteCashInvoiceRequestTR(
                            mailTo,
                            currentUser.getEmail(),
                            incomeDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getName() + " " + incomeDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getSurname(),
                            message,
                            currentUser.getName() + " " + currentUser.getSurname(),
                            formatDate,
                            taskConfirmType
                    );
                }
                else if (incomeDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getUserlanguage().equals("en")){
                    mailService.deleteCashInvoiceRequest(
                            mailTo,
                            currentUser.getEmail(),
                            incomeDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getName() + " " + incomeDTO.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getSurname(),
                            message,
                            currentUser.getName() + " " + currentUser.getSurname(),
                            formatDate,
                            taskConfirmType
                    );
                }

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        String state;
        if(t.getModuleType().getModuleTypeEnum() == ModuleTypeEnum.COMPANY_CREATE){
            if(taskConfirmType == TaskConfirmEnum.DONE){
                state="3";
            }
            else if(taskConfirmType == TaskConfirmEnum.REJECTED){
                state="2";
            }
            else{
                state="1";
            }
            clientRepository.changeState(t.getClient().getId(),state);
        }
        return taskConfirmationRepository.save(taskConfirmation);
    }

    public  List<ModuleTypesResponse> getModuleTypes(){
        List<ModuleTypesResponse> m = taskModuleTypesRepository.findAllModuleTypes();
        return m;
    }

    public List<TaskConfirmation> get(long id) {
        List<TaskConfirmation> confirmations= taskConfirmationRepository.findById(id);
        return confirmations;
    }

    public List<TaskConfirmationDTO> getTasksByPersonelId(long id) {
        List<TaskConfirmation> confirmations= taskConfirmationRepository.findById(id);
        return taskConfirmationMapper.toDto(confirmations);
    }

    public List<TaskConfirmation> getInProgressTasksByDate(){

        List<TaskConfirmation> done1 = taskConfirmationRepository.findDone();
        List<TaskConfirmationDTO> done = taskConfirmationMapper.toDto(done1);

        List<Long> isDone = new ArrayList<>();
        LocalDateTime progress = LocalDateTime.now();
        LocalDateTime insert = LocalDateTime.now();

        for(TaskConfirmationDTO tsk:done){
           isDone.add(tsk.getTasks().getId());
        }
        List<TaskConfirmation> inProgress = taskConfirmationRepository.findInProgress();
        System.out.println(inProgress.size()+" ilk");
        List<Long> isIn = new ArrayList<>();
        List<TaskConfirmation> toDelete = new ArrayList<TaskConfirmation>();
        for(TaskConfirmation taskConfirmation : inProgress){
            isIn.add(taskConfirmation.getTasks().getId());
            System.out.println(isIn);
            System.out.println(taskConfirmation);
            for(TaskConfirmationDTO compare: done){
                if(compare.getTasks().getId() == taskConfirmation.getTasks().getId()){
                    toDelete.add(taskConfirmation);
                }else{
                    progress = LocalDateTime.parse(compare.getProcessDate().toString());
                    insert =LocalDateTime.parse(taskConfirmation.getCreatedDateTime().toString());
                }
            }

            System.out.println(progress.compareTo(insert) + "demek");
        }
        inProgress.removeAll(toDelete);

        System.out.println(inProgress.size()+" son");
        return inProgress;

    }

    public List<TaskConfirmation> getDoneTasksByDate(){
        List<TaskConfirmation> done = taskConfirmationRepository.findDone();
        for(TaskConfirmation taskConfirmation : done){
            LocalDateTime progress = taskConfirmation.getProcessDate();
            LocalDateTime insert =taskConfirmation.getCreatedDateTime();
        }
        return done;
    }
}
