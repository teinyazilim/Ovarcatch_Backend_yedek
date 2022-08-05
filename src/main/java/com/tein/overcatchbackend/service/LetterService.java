package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.dto.ClientDTO;
import com.tein.overcatchbackend.domain.dto.HelpDTO;
import com.tein.overcatchbackend.domain.dto.LetterDTO;
import com.tein.overcatchbackend.domain.model.*;
import com.tein.overcatchbackend.enums.ModuleTypeEnum;
import com.tein.overcatchbackend.enums.TaskConfirmEnum;
import com.tein.overcatchbackend.enums.UserType;
import com.tein.overcatchbackend.mapper.LetterMapper;
import com.tein.overcatchbackend.repository.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LetterService {

    private final LetterRepository letterRepository;
    private final CurrentUserService currentUserService;
    private final TaskService taskService;
    private final LetterMapper letterMapper;
    private final ModuleTypeRepository moduleTypeRepository;
    private final TaskConfirmationRepository taskConfirmationRepository;
    private final TaskRepository taskRepository;
    private final MailService mailService;
    private final FileStorageService fileStorageService;
    private final DocumentService documentService;
    private final LetterTypeRepository letterTypeRepository;

    public Letter saveLetter(Letter letter) {
        Client client = new Client();
        ModuleType moduleType=moduleTypeRepository.findByModuleTypeEnum1(ModuleTypeEnum.LETTER_MODULE.toString());
        client.setId(letter.getClient().getId());
        User user = currentUserService.getCurrentUser();
        letter.setUserRole(user.getUserType().toString());
        if (letter.getId() == null){
            Tasks tasks=taskService.addTask(client, moduleType);
            letter.setTask(tasks);
        }else{
            Letter letter1=letterRepository.findById(letter.getId()).get();
            letter.setTask(letter1.getTask());
        }
        return letterRepository.save(letter);
    }

    public Letter saveDoneLetter(Letter letter) {
        Letter finalLetter = new Letter();
        try{
            Client client = new Client();
            ModuleType moduleType=moduleTypeRepository.findByModuleTypeEnum1(ModuleTypeEnum.LETTER_MODULE.toString());
            client.setId(letter.getClient().getId());
            User user = currentUserService.getCurrentUser();
            letter.setUserRole(user.getUserType().toString());
            finalLetter = letterRepository.save(letter);

            Resource resourceDoc = null;
            if (finalLetter.getDocument() != null)
                resourceDoc = fileStorageService.loadFileAsResource(letter.getDocument().getFileName(), letter.getClient().getClientFolder());

            String pdfFileName = documentService.createpdf(letterMapper.toDto(finalLetter));
            Resource resourcePdf = fileStorageService.loadFileAsResource(pdfFileName, letter.getClient().getClientFolder());

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            String mailTo = finalLetter.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getEmail();
            if(finalLetter.getClient().getFounderOwner() != null){
                if(finalLetter.getClient().getFounderOwner().getEmail() != null)
                    mailTo = finalLetter.getClient().getFounderOwner().getEmail();
            }else if(finalLetter.getClient().getCompany() != null){
                if(finalLetter.getClient().getCompany().getEmail() != null)
                    mailTo = finalLetter.getClient().getCompany().getEmail();
            }

            //Eğer Kullanıcnın user_language kısmı null ise default olarak Ingilizce olan template gönderiyorum ...
            if(letter.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getUserlanguage() == null){
                letter.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().setUserlanguage("en");
            }

            if(letter.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getUserlanguage().equals("tr")){
                mailService.sendLetterFromManagerTR(mailTo,
                        user.getEmail() ,resourceDoc != null ? resourceDoc.getFile() : null ,
                        resourcePdf != null ? resourcePdf.getFile() : null,
                        finalLetter.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getName() + " " + finalLetter.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getSurname(),
                        null,
                        user.getName() + " " + user.getSurname(),
                        now.format(dtf));
            }
            else if (letter.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getUserlanguage().equals("en")){
                mailService.sendLetterFromManager(mailTo,
                        user.getEmail() ,resourceDoc != null ? resourceDoc.getFile() : null ,
                        resourcePdf != null ? resourcePdf.getFile() : null,
                        finalLetter.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getName() + " " + finalLetter.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getSurname(),
                        null,
                        user.getName() + " " + user.getSurname(),
                        now.format(dtf));
            }

            documentService.deleteFileFromDisk(finalLetter.getClient().getClientFolder(), pdfFileName);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return finalLetter;
    }

    public List<Letter> getLetters() {
        List<Letter> letters = letterRepository.findAll();
        List<Letter> filteredList = Collections.<Letter>emptyList();
        for (Letter letter1 : letters) {
            if (letter1.getTask() != null)
                letter1.getTask().getTaskConfirmations().sort((o1, o2) -> o1.getProcessDate().isBefore( o2.getProcessDate()) ? 1 : -1);
        }
        try {
             filteredList =  letters.stream().filter(letter ->
                    letter.getTask() == null ||
                            ((letter.getTask().getTaskConfirmations().size() != 0) ?
                            letter.getTask().getTaskConfirmations().get(0).getTaskConfirm() == TaskConfirmEnum.DONE : false)
            ).collect(Collectors.toList());

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return filteredList;
    }

    public LetterDTO getLetterByID(long letterID) {
        Letter letter = letterRepository.findById(letterID).get();
        LetterDTO letterDTO = letterMapper.toDto(letter);

        return letterDTO;
    }
    public LetterDTO getLetterByTaskId(long taskId) {
        Letter letter = letterRepository.findByTaskId(taskId);
        LetterDTO letterDTO = letterMapper.toDto(letter);
        return letterDTO;
    }
    public Page<Letter> getLetterByClientId(long clientId, String orderColumn, String orderBy, int page, int size) {


        int currentPage = page;
        int pageSize = size == 0 ? 5 : size;
        Sort sort = Sort.by(Sort.Direction.fromString(orderBy), orderColumn);
        Pageable pageable = PageRequest.of(currentPage,pageSize, sort);

        Page<Letter> letters = letterRepository.findAllByClientId(clientId, pageable);
        return letters;
    }

    public List<Letter> getCustomLetters(){
        return letterRepository.findAllByLetterTypeIsNull();
    }
    public Page<Letter> getLettersByMultiFilter(String letterType, String search, String orderColumn, String orderBy, int page, int size) {
        List<Letter> dto;
        List<String> letterTypes = new ArrayList<String>();
         try{
            if(StringUtils.isEmpty(letterType)){
                List<LetterType> letterTypes1 = letterTypeRepository.findAll();
                for (LetterType letterType1: letterTypes1) {
                    letterTypes.add(letterType1.getId().toString());
                }
            }else{
                letterTypes = new ArrayList<String>(Arrays.asList(letterType.split(",")));
            }
            //letterType = StringUtils.isEmpty(letterType) ? "" : letterType;

        }catch (Exception e){
            System.out.println("Error Message: " + e.getMessage());
        }
//        if(currentUserService.getCurrentUser().getCustomer()!=null){
//            dto= clientRepository.findAllApplyByFilterByUserId(aggrementType, clientType, exist, state, search, currentUserService.getCurrentUser().getCustomer().getId());
//        }
//        else{
//            dto= letterRepository.findAllLettersByMultiFilter(letterTypes, search);

        int currentPage = page;
        int pageSize = size == 0 ? 5 : size;
        Sort sort = Sort.by(Sort.Direction.fromString(orderBy), orderColumn);
        Pageable pageable = PageRequest.of(currentPage,pageSize, sort);
        Page<Letter> letters = letterRepository.findAllLettersByMultiFilter(letterTypes, search, pageable);


//        }
        return letters;
    }

    public Letter editLetter(LetterDTO letterDTO) {
        Letter letter = letterRepository.findById(letterDTO.getId()).get();
        letter.setLetter(letterDTO.getLetter());
        return letterRepository.save(letter);
    }
}

