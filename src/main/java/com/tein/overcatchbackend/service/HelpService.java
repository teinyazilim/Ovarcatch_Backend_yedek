package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.dto.HelpDTO;
import com.tein.overcatchbackend.domain.model.*;
import com.tein.overcatchbackend.domain.dto.HelpViewDTO;
import com.tein.overcatchbackend.domain.model.Help;
import com.tein.overcatchbackend.enums.ModuleTypeEnum;
import com.tein.overcatchbackend.enums.TaskConfirmEnum;
import com.tein.overcatchbackend.mapper.HelpMapper;
import com.tein.overcatchbackend.repository.DirectorDetailRepository;
import com.tein.overcatchbackend.repository.HelpRepository;
import com.tein.overcatchbackend.repository.ModuleTypeRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class HelpService {

    private final HelpRepository helpRepository;
    private final TaskService taskService;
    private final HelpMapper helpMapper;
    private final MailService mailService;
    private final DirectorDetailRepository directorDetailRepository;
    @Autowired
    private final ModelMapper modelMapper;
    private final CurrentUserService currentUserService;
    private final ModuleTypeRepository moduleTypeRepository;
    private final HelpTypeService helpTypeService;

    public Help saveHelp(Help help) {

        ModuleType moduleType=moduleTypeRepository.findByModuleTypeEnum1(ModuleTypeEnum.HELP_MODULE.toString());

        if (help.getId() == null){

            //User user=currentUserService.getCurrentUser();
            //mailService.sendMailEmployee("ik@tein.com.tr",user.getName()+" "+user.getSurname(),help.getHelpType().getHelpTypeShowName(),help.getDescription());

            Tasks tasks = taskService.addTask(help.getClient(), moduleType);
            help.setTask(tasks);
        }else{

            Help help1=helpRepository.findById(help.getId()).get();
            help.setTask(help1.getTask());
        }

        helpRepository.save(help);

        return help;

    }

    public Help sendSupportTicket(Help help , MultipartFile file) {

         ModuleType moduleType=moduleTypeRepository.findByModuleTypeEnum1(ModuleTypeEnum.HELP_MODULE.toString());
//         DirectorDetail directorDetail = directorDetailRepository.getOne(Long.valueOf(help.getDivided()));
//         System.out.println(directorDetail + " divided");

         if (file == null){
            // Gönderilmek istenilen e-posta dökümansız olarak gönderiliyor ise ;
            String[] help_emails1 = help.getHelpType().getEmail().split(";");

            for (String toEmail: help_emails1
            ) {
                mailService.sendSupportTicket(toEmail , help.getDescription() , help.getHelpType().getHelpTypeShowName());
            }
        }
        else {
            // Gönderilmek istenilen e-posta dökümanlı olarak gönderiliyor ise ;
            String[] help_emails1 = help.getHelpType().getEmail().split(";");
            for (String toEmail: help_emails1
            ) {
                try {
                    mailService.sendSupportTicketWithAttachment(toEmail , help.getHelpType().getHelpTypeShowName() ,help.getDescription() ,file);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }

        if (help.getId() == null){

            //User user=currentUserService.getCurrentUser();
            //mailService.sendMailEmployee("ik@tein.com.tr",user.getName()+" "+user.getSurname(),help.getHelpType().getHelpTypeShowName(),help.getDescription());

            Tasks tasks = taskService.addTask(help.getClient(), moduleType);
            help.setTask(tasks);
        }else{

            Help help1=helpRepository.findById(help.getId()).get();
            help.setTask(help1.getTask());
        }

        helpRepository.save(help);

        return help;

    }


    public void answerTicket(Help help) {

        ModuleType moduleType = moduleTypeRepository.findByModuleTypeEnum1(ModuleTypeEnum.HELP_MODULE.toString());
        String mailTo = "";
        String senderMail = "";
        String answerText = "";
        String topicOfRequest = "";

            try{
                //Mail Gönderme İslemi
                senderMail = help.getSupport_user().getEmail();
                mailTo = help.getRequest_user().getEmail();
                answerText = help.getAnswer();
                topicOfRequest = help.getHelpType().getHelpTypeShowName();
                // Burada Cevap verilen kişinin dil bilgisine göre kodlanacak !

                //Eğer Kullanıcnın user_language kısmı null ise default olarak Ingilizce olan template gönderiyorum ...
                if(help.getRequest_user().getUserlanguage() == null){
                    help.getRequest_user().setUserlanguage("en");
                }

                if(help.getRequest_user().getUserlanguage().equals("tr")){
                    mailService.sendAnswerTicketTR(mailTo , senderMail ,answerText ,topicOfRequest);
                }
                else if(help.getRequest_user().getUserlanguage().equals("en")){
                    mailService.sendAnswerTicket(mailTo , senderMail ,answerText ,topicOfRequest);
                }
                System.out.println("Mail Gönderilen Addres : "+ mailTo);

            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        if (help.getId() == null){
            Tasks tasks = taskService.addTask(help.getClient(), moduleType);
            help.setTask(tasks);
        }
        else{
            Help help1=helpRepository.findById(help.getId()).get();
            help.setTask(help1.getTask());
        }
        //Yapılan islemler support kısmına kayit ediliyor !
        helpRepository.save(help);
    }

    public List<HelpDTO> getHelps() {
        List<Help> helpList = helpRepository.findAllHelp();
        List<HelpDTO> helpDTO = helpMapper.toDto(helpList);
        return helpDTO;
    }

    public List<HelpDTO> getSupportList(String search) {
        List<Help> helpList = helpRepository.findSupport(search);
        List<HelpDTO> helpDTO = helpMapper.toDto(helpList);
        return helpDTO;
    }

    public List<HelpDTO> getSupportListFilter(String search , String supportType,String statusType) {

        List<String> supportTypeList = new ArrayList<String>();
        List<String> statusTypeList = new ArrayList<String>();

        if (StringUtils.isEmpty(supportType)){
            List<HelpType> helpTypes = helpTypeService.getHelpTypes();
            for (HelpType type:helpTypes) {
                supportTypeList.add(type.getHelpTypeShowName());
            }
        }
        else {
            supportTypeList = new ArrayList<String>(Arrays.asList(supportType.split(",")));
        }

        if (StringUtils.isEmpty(statusType)){
            statusTypeList.add(TaskConfirmEnum.INPROGRESS.toString());
            statusTypeList.add("PENDING");
        }
        else {
            statusTypeList = new ArrayList<String>(Arrays.asList(statusType.split(",")));
        }
        List<Help> helpList = helpRepository.findSupportFilter(search,supportTypeList,statusTypeList);
        List<HelpDTO> helpDTO = helpMapper.toDto(helpList);
        return helpDTO;
    }

    public HelpDTO getHelpByID(long helpID) {
        Help help = helpRepository.findById(helpID).get();
        HelpDTO helpDTO = helpMapper.toDto(help);

        return helpDTO;
    }

    public HelpDTO getHelpByTaskId(long taskId) {
        Help help = helpRepository.findByTaskId(taskId);
        HelpDTO helpDTO = helpMapper.toDto(help);
        return helpDTO;
    }

    public List<HelpDTO> getHelpsByClientId(long clientId) {
        List<Help> help = helpRepository.findAllByClientId(clientId);
        List<HelpDTO> helpDTO = helpMapper.toDto(help);
        return helpDTO;
    }
    //Git denemesi için eklendi
}

