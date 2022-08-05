package com.tein.overcatchbackend.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.el.lang.ELSupport;
import com.tein.overcatchbackend.domain.dto.ClientDTO;
import com.tein.overcatchbackend.domain.dto.DirectorDetailDTO;
import com.tein.overcatchbackend.domain.dto.NoticeCreateDTO;
import com.tein.overcatchbackend.domain.dto.NoticeLogDTO;
import com.tein.overcatchbackend.domain.model.*;
import com.tein.overcatchbackend.mapper.ClientMapper;
import com.tein.overcatchbackend.mapper.NoticeLogMapper;
import com.tein.overcatchbackend.repository.ClientRepository;
import com.tein.overcatchbackend.repository.NoticeLogRepository;
import com.tein.overcatchbackend.enums.ClientTypeEnum;
import com.tein.overcatchbackend.mapper.CompanyMapper;
import com.tein.overcatchbackend.repository.CompanyRepository;
import com.tein.overcatchbackend.repository.NotificationsUsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


@Slf4j
@Service
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoticeService {
    private final MailService mailService;
    private final NoticeLogMapper noticeLogMapper;
    private final ClientRepository clientRepository;
    private final CurrentUserService currentUserService;
    private final NoticeLogRepository noticeLogRepository;
    private final ClientMapper clientMapper;
    private final FileStorageService fileStorageService;
    private final NotificationsUsersRepository notificationsUsersRepository;

    public List<NoticeLogDTO> getNotice() {
        List<NoticeLog> noticeLogList = noticeLogRepository.findAll();
        return noticeLogMapper.toDto(noticeLogList);
    }

    //Notification-Create Sayfasında yeni bir bildirim oluşturmak istediğinde çalışan metot
    //Bu metot ilgili bildirimi notice_log tablosuna kayıt yapıyor...
    //Notication klasörüne kayıt yapıyor ...
    //Ve ilgili kişilere mail gönderiyor ...
    @JsonProperty("noticeCreateDTO")
    public ResponseEntity<?> sender(NoticeCreateDTO noticeCreateDTO , MultipartFile file) {
        try {

            //Gelen Data Bilgileri Notice_log Tablosuna Kaydedildi .
            NoticeLog noticeLog = new NoticeLog();
            noticeLog.setNotiType("Mail");
            noticeLog.setSubject(noticeCreateDTO.getSubject());
            noticeLog.setMessage(noticeCreateDTO.getContent());
            if ( file != null){
                //Eğer Eklenen bildirim dosyalı ise bunu diske kaydediyorum ...
                Document document = fileStorageService.loadFilefromNotification(file);
                noticeLog.setDocument(document);
            }
            NoticeLog finalNoticeLog =  noticeLogRepository.save(noticeLog);
            //Yukarıdaki kodda Notice_log Tablosuna kaydettiğim verinin id bilgisini
            //NoticeUsers tablosuna id'sini setlemek için finalNoticeLog değişkenine
            //Setliyorum ardından notificationUsers objesine veriyi setliyorum .
            if ( file == null){
                // Dosyasız email gönderme ve db ye kaydetme işlemi .
                //List<Client> clientList = clientMapper.toEntity(noticeCreateDTO.getClientList());
                List<ClientDTO> clientList = noticeCreateDTO.getClientList();
                for (ClientDTO client : clientList) {

                    //Eğer Kullanıcnın user_language kısmı null ise default olarak Ingilizce olan template gönderiyorum ...
                    if(client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage() == null){
                        client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().setUserlanguage("en");
                    }

                    if (client.getFounderOwner() != null && client.getFounderOwner().getEmail() != null){
                        //Company Kısmı Null
                        if (noticeCreateDTO.isMail() && client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage().equals("tr"))
                        {
                            //Türkçe içerikli Template gönderilme işlemi
                            //Dosyasız - email gönderme işlemi
                            String mail = mailService.sendNoticeMailHtmlTR(
                                                            client.getFounderOwner().getEmail() ,
                                                            noticeCreateDTO.getSubject() ,
                                                            noticeCreateDTO.getContent() ,
                                                           client.getFounderOwner().getName() + ' '+ client.getFounderOwner().getSurname());

                            NotificationsUsers notificationsUsers = new NotificationsUsers();
                            notificationsUsers.setEmail(client.getFounderOwner().getEmail());
                            notificationsUsers.setNoticeLog(finalNoticeLog);
                            notificationsUsers.setStatus(mail.equals("Mail Sent!") ? 1 : 0);
                            notificationsUsers.setClient(clientMapper.toEntity(client));
                            notificationsUsersRepository.save(notificationsUsers);
                        }
                        else if (noticeCreateDTO.isMail() && client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage().equals("en")) {
                            //Inglizce içerikli Template gönderilme işlemi
                            //Dosyasız - email gönderme işlemi
                            String mail = mailService.sendNoticeMailHtml(
                                    client.getFounderOwner().getEmail() ,
                                    noticeCreateDTO.getSubject() ,
                                    noticeCreateDTO.getContent() ,
                                    client.getFounderOwner().getName() + ' '+ client.getFounderOwner().getSurname());

                            NotificationsUsers notificationsUsers = new NotificationsUsers();
                            notificationsUsers.setEmail(client.getFounderOwner().getEmail());
                            notificationsUsers.setNoticeLog(finalNoticeLog);
                            notificationsUsers.setStatus(mail.equals("Mail Sent!") ? 1 : 0);
                            notificationsUsers.setClient(clientMapper.toEntity(client));
                            notificationsUsersRepository.save(notificationsUsers);
                        }
                    }
                    else if (client.getCompany() != null && client.getCompany().getDirectorDetails().size() > 0){
                        // FounderOwer Kısmı null
                        // Ve ben directory email göndereceğim
                        if (noticeCreateDTO.isMail() && client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage().equals("tr")) {
                            for (DirectorDetailDTO directoryDetail: client.getCompany().getDirectorDetails()
                            ) {
                                if (directoryDetail.getEmail() != null){

                                    System.out.println(directoryDetail.getEmail());
                                    System.out.println(noticeCreateDTO.getSubject());
                                    System.out.println(noticeCreateDTO.getContent());
                                    System.out.println(directoryDetail.getName());
                                    // Türkçe Mail Seçeneği eklenecek !
                                    String mail = mailService.sendNoticeMailHtmlTR(
                                                                        directoryDetail.getEmail(),
                                                                        noticeCreateDTO.getSubject() ,
                                                                        noticeCreateDTO.getContent() ,
                                                                        directoryDetail.getName() + ' ' + directoryDetail.getSurname());

                                    NotificationsUsers notificationsUsers = new NotificationsUsers();
                                    notificationsUsers.setEmail(directoryDetail.getEmail());
                                    notificationsUsers.setNoticeLog(finalNoticeLog);
                                    notificationsUsers.setStatus(mail.equals("Mail Sent!") ? 1 : 0);
                                    notificationsUsers.setClient(clientMapper.toEntity(client));
                                    notificationsUsersRepository.save(notificationsUsers);
                                }
                            }
                        }
                        else if(noticeCreateDTO.isMail() && client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage().equals("en"))
                        {
                            for (DirectorDetailDTO directoryDetail: client.getCompany().getDirectorDetails()
                            ) {
                                if (directoryDetail.getEmail() != null){
                                    // Ingilizce içerikli Template gönderme işlemi ...
                                    System.out.println(directoryDetail.getEmail());
                                    System.out.println(noticeCreateDTO.getSubject());
                                    System.out.println(noticeCreateDTO.getContent());
                                    System.out.println(directoryDetail.getName());

                                    String mail = mailService.sendNoticeMailHtml(
                                            directoryDetail.getEmail(),
                                            noticeCreateDTO.getSubject() ,
                                            noticeCreateDTO.getContent() ,
                                            directoryDetail.getName() + ' ' + directoryDetail.getSurname());

                                    NotificationsUsers notificationsUsers = new NotificationsUsers();
                                    notificationsUsers.setEmail(directoryDetail.getEmail());
                                    notificationsUsers.setNoticeLog(finalNoticeLog);
                                    notificationsUsers.setStatus(mail.equals("Mail Sent!") ? 1 : 0);
                                    notificationsUsers.setClient(clientMapper.toEntity(client));
                                    notificationsUsersRepository.save(notificationsUsers);
                                }
                            }
                        }
                    }
                    //if (noticeCreateDTO.isSms()) {
                            //ToDo Sms bildirimleri bu alanda yapılacak
                            //}
                            //if (noticeCreateDTO.isApp()) {
                            //ToDo Mobil uygulama bildirimleri bu alanda yapılacak
                            //}
                }
            }
            else {
                // Dosyalı email gönder ve ve disk & DB ' ye veri kaydetme işlemi .
                System.out.println(file.getName());
                //List<Client> clientList = clientMapper.toEntity(noticeCreateDTO.getClientList());
                List<ClientDTO> clientList = noticeCreateDTO.getClientList();
                for (ClientDTO client : clientList) {
                    //Eğer Kullanıcnın user_language kısmı null ise default olarak Ingilizce olan template gönderiyorum ...
                    if(client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage() == null){
                        client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().setUserlanguage("en");
                    }
                    if (client.getFounderOwner() != null && client.getFounderOwner().getEmail() != null){
                        //Company Kısmı Null
                        if ( noticeCreateDTO.isMail() && client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage().equals("tr")) {
                            // Dosya boyutu Max. MG fazla olamayacak bu kontrolu sağla ....
                            // Türkçe Mail gönderme işlemi ...
                            if ( file.getSize() <= 20971520 ){
                                String mail = mailService.sendNoticeMailHtmlWithAttachmentTR(
                                                                    client.getFounderOwner().getEmail() ,
                                                                    noticeCreateDTO.getSubject() ,
                                                                    noticeCreateDTO.getContent() ,
                                                                   client.getFounderOwner().getName() + ' '+ client.getFounderOwner().getSurname() ,
                                                                    file);

                                NotificationsUsers notificationsUsers = new NotificationsUsers();
                                notificationsUsers.setEmail(client.getFounderOwner().getEmail());
                                notificationsUsers.setNoticeLog(finalNoticeLog);
                                notificationsUsers.setStatus(mail.equals("Mail Sent!") ? 1 : 0);
                                notificationsUsers.setClient(clientMapper.toEntity(client));
                                notificationsUsersRepository.save(notificationsUsers);
                            }
                        }
                        else if (noticeCreateDTO.isMail() && client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage().equals("en"))
                        {
                            // Dosya boyutu Max. MG fazla olamayacak bu kontrolu sağla ....
                            // Inglizce Mail gönderme işlemi ...
                            if ( file.getSize() <= 20971520 ){
                                String mail = mailService.sendNoticeMailHtmlWithAttachment(
                                        client.getFounderOwner().getEmail() ,
                                        noticeCreateDTO.getSubject() ,
                                        noticeCreateDTO.getContent() ,
                                        client.getFounderOwner().getName() + ' '+ client.getFounderOwner().getSurname() ,
                                        file);

                                NotificationsUsers notificationsUsers = new NotificationsUsers();
                                notificationsUsers.setEmail(client.getFounderOwner().getEmail());
                                notificationsUsers.setNoticeLog(finalNoticeLog);
                                notificationsUsers.setStatus(mail.equals("Mail Sent!") ? 1 : 0);
                                notificationsUsers.setClient(clientMapper.toEntity(client));
                                notificationsUsersRepository.save(notificationsUsers);
                            }
                        }
                    }
                    else if (client.getCompany() != null && client.getCompany().getDirectorDetails().size() > 0){
                        // FounderOwer Kısmı null
                        // Ve ben directory email göndereceğim
                        if (noticeCreateDTO.isMail() && client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage().equals("tr")) {
                            for (DirectorDetailDTO directoryDetail: client.getCompany().getDirectorDetails()
                            ) {
                                if (directoryDetail.getEmail() != null){
                                    //Türkçe Mail gönderme işlemi ...
                                    System.out.println(directoryDetail.getEmail());
                                    System.out.println(noticeCreateDTO.getSubject());
                                    System.out.println(noticeCreateDTO.getContent());
                                    System.out.println(directoryDetail.getName());

                                    // Dosya boyutu Max. 20 MB fazla olamayacak bu kontrolu sağla ....
                                    if ( file.getSize() <= 20971520){
                                        String mail = mailService.sendNoticeMailHtmlWithAttachmentTR(
                                                                                    directoryDetail.getEmail(),
                                                                                    noticeCreateDTO.getSubject() ,
                                                                                    noticeCreateDTO.getContent() ,
                                                                                    directoryDetail.getName() + ' ' + directoryDetail.getSurname(),
                                                                                    file);
                                        NotificationsUsers notificationsUsers = new NotificationsUsers();
                                        notificationsUsers.setEmail(directoryDetail.getEmail());
                                        notificationsUsers.setNoticeLog(finalNoticeLog);
                                        notificationsUsers.setStatus(mail.equals("Mail Sent!") ? 1 : 0);
                                        notificationsUsers.setClient(clientMapper.toEntity(client));
                                        notificationsUsersRepository.save(notificationsUsers);
                                    }
                                }
                            }
                        }
                        else if (noticeCreateDTO.isMail() && client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage().equals("en"))
                        {
                            for (DirectorDetailDTO directoryDetail: client.getCompany().getDirectorDetails()
                            ) {
                                if (directoryDetail.getEmail() != null){
                                    //Ingilizce Mail gönderme işlemi ...
                                    System.out.println(directoryDetail.getEmail());
                                    System.out.println(noticeCreateDTO.getSubject());
                                    System.out.println(noticeCreateDTO.getContent());
                                    System.out.println(directoryDetail.getName());

                                    // Dosya boyutu Max. 20 MB fazla olamayacak bu kontrolu sağla ....
                                    if ( file.getSize() <= 20971520){
                                        String mail = mailService.sendNoticeMailHtmlWithAttachment(
                                                directoryDetail.getEmail(),
                                                noticeCreateDTO.getSubject() ,
                                                noticeCreateDTO.getContent() ,
                                                directoryDetail.getName() + ' ' + directoryDetail.getSurname(),
                                                file);
                                        NotificationsUsers notificationsUsers = new NotificationsUsers();
                                        notificationsUsers.setEmail(directoryDetail.getEmail());
                                        notificationsUsers.setNoticeLog(finalNoticeLog);
                                        notificationsUsers.setStatus(mail.equals("Mail Sent!") ? 1 : 0);
                                        notificationsUsers.setClient(clientMapper.toEntity(client));
                                        notificationsUsersRepository.save(notificationsUsers);
                                    }
                                }
                            }
                        }
                    }
                            //if (noticeCreateDTO.isSms()) {
                            //ToDo Sms bildirimleri bu alanda yapılacak
                            //}
                            //if (noticeCreateDTO.isApp()) {
                            //ToDo Mobil uygulama bildirimleri bu alanda yapılacak
                            //}
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

    //Notification sayfası resend edildiğin de tetiklenen metot ...
    @JsonProperty("noticeCreateDTO")
    public ResponseEntity<?> resendNotification(NoticeCreateDTO noticeCreateDTO ) {
        try {
            Resource resourceDoc = null;
            //Aşağıdaki kodda notification_log tablosunda bulunan document_id sayesinde
            //istediğim Id'ye bağlı olarak bilgileri getirebiliyorum dolayısıyla bana
            // Dokümanın bilgiside gelmiş oluyor .
            NoticeLog noticeLogDoc = noticeLogRepository.findById(noticeCreateDTO.getId()).get();
            //List<Client> clientList = clientMapper.toEntity(getClientByNotifications(noticeCreateDTO.getId()));
            List<ClientDTO> clientList = noticeCreateDTO.getClientList();
            if (noticeLogDoc.getDocument() != null)
                resourceDoc = fileStorageService.loadFileAsResource(noticeLogDoc.getDocument().getFileName(),noticeLogDoc.getDocument().getFilePath());
            System.out.println(resourceDoc);
            for (ClientDTO client : clientList) {

                //Eğer Kullanıcnın user_language kısmı null ise default olarak Ingilizce olan template gönderiyorum ...
                if(client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage() == null){
                    client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().setUserlanguage("en");
                }

                if (client.getFounderOwner() != null && client.getFounderOwner().getEmail() != null){
                    //Company Kısmı Null Ve ben founder email göndereceğim
                    if (noticeCreateDTO.isMail() && client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage().equals("tr")) {
                        //Eğer Resend edildiğinde dökümanı yok ise dökümansız email göndereceğim
                        //Resend edilecek kullanıcının user_language kısımı 'tr' ise ilgili şekilde mail gönderilecek ...
                        if( noticeLogDoc.getDocument() == null){
                            String mail = mailService.sendNoticeMailHtmlTR(
                                                                client.getFounderOwner().getEmail() ,
                                                                noticeCreateDTO.getSubject() ,
                                                                noticeCreateDTO.getContent() ,
                                                                client.getFounderOwner().getName() + ' ' +client.getFounderOwner().getSurname());
                            System.out.println(mail);
                        }
                        else if (noticeLogDoc.getDocument() != null){
                            //Eğer resend edilecek veride döküman varsa email dökümanlı gönderilecek .
                            System.out.println("Dökümanlı Veri Resend edildi .");
                            //Bu kısım düzeltilecek....
                            String mail = mailService.resendNoticeWithAttachmentTR(
                                                                            client.getFounderOwner().getEmail(),
                                                                            noticeCreateDTO.getSubject(),
                                                                            noticeCreateDTO.getContent(),
                                                                            client.getFounderOwner().getName() + ' ' +client.getFounderOwner().getSurname(),
                                                                            resourceDoc.getFile());
                            System.out.println(mail);
                        }
                    }
                    else if(noticeCreateDTO.isMail() && client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage().equals("en")){
                        //Eğer Resend edildiğinde dökümanı yok ise dökümansız email göndereceğim
                        //Resend edilecek kullanıcının user_language kısımı 'tr' ise ilgili şekilde mail gönderilecek ...
                        if( noticeLogDoc.getDocument() == null){
                            String mail = mailService.sendNoticeMailHtml(
                                    client.getFounderOwner().getEmail() ,
                                    noticeCreateDTO.getSubject() ,
                                    noticeCreateDTO.getContent() ,
                                    client.getFounderOwner().getName() + ' ' +client.getFounderOwner().getSurname());
                            System.out.println(mail);
                        }
                        else if (noticeLogDoc.getDocument() != null){
                            //Eğer resend edilecek veride döküman varsa email dökümanlı gönderilecek .
                            System.out.println("Dökümanlı Veri Resend edildi .");
                            //Bu kısım düzeltilecek....
                            String mail = mailService.resendNoticeWithAttachment(
                                    client.getFounderOwner().getEmail(),
                                    noticeCreateDTO.getSubject(),
                                    noticeCreateDTO.getContent(),
                                    client.getFounderOwner().getName() + ' ' +client.getFounderOwner().getSurname(),
                                    resourceDoc.getFile());
                            System.out.println(mail);
                        }
                    }
                }
                else if (client.getCompany() != null && client.getCompany().getDirectorDetails().size() > 0){
                    // FounderOwer Kısmı null Ve ben directory email göndereceğim
                    if (noticeCreateDTO.isMail() && client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage().equals("tr")) {
                        // Türkçe Mail Göndereceğim
                        for (DirectorDetailDTO directoryDetail: client.getCompany().getDirectorDetails()
                        ) {
                            if (directoryDetail.getEmail() != null){
                                System.out.println(directoryDetail.getEmail());
                                System.out.println(noticeCreateDTO.getSubject());
                                System.out.println(noticeCreateDTO.getContent());
                                System.out.println(directoryDetail.getName());
                                if( noticeLogDoc.getDocument() == null){
                                    String mail = mailService.sendNoticeMailHtmlTR(
                                                                        directoryDetail.getEmail(),
                                                                        noticeCreateDTO.getSubject() ,
                                                                        noticeCreateDTO.getContent() ,
                                                                        directoryDetail.getName() + ' ' + directoryDetail.getSurname());
                                    System.out.println(mail);
                                }
                                else if (noticeLogDoc.getDocument() != null){
                                    //Eğer resend edilecek veride döküman varsa email dökümanlı gönderilecek .
                                    System.out.println("Dökümanlı veri resend edildi .");
                                    String mail = mailService.resendNoticeWithAttachmentTR(directoryDetail.getEmail(),
                                                                                                noticeCreateDTO.getSubject(),
                                                                                                noticeCreateDTO.getContent(),
                                                                                                directoryDetail.getName() + ' ' + directoryDetail.getSurname(),
                                                                                                resourceDoc.getFile());
                                    System.out.println(mail);
                                }
                            }
                        }
                    }
                    else if (noticeCreateDTO.isMail() && client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage().equals("en")) {
                        for (DirectorDetailDTO directoryDetail: client.getCompany().getDirectorDetails()
                        ) {
                            if (directoryDetail.getEmail() != null){
                                System.out.println(directoryDetail.getEmail());
                                System.out.println(noticeCreateDTO.getSubject());
                                System.out.println(noticeCreateDTO.getContent());
                                System.out.println(directoryDetail.getName());
                                if( noticeLogDoc.getDocument() == null){
                                    String mail = mailService.sendNoticeMailHtml(
                                            directoryDetail.getEmail(),
                                            noticeCreateDTO.getSubject() ,
                                            noticeCreateDTO.getContent() ,
                                            directoryDetail.getName() + ' ' + directoryDetail.getSurname());
                                    System.out.println(mail);
                                }
                                else if (noticeLogDoc.getDocument() != null){
                                    //Eğer resend edilecek veride döküman varsa email dökümanlı gönderilecek .
                                    System.out.println("Dökümanlı veri resend edildi .");
                                    String mail = mailService.resendNoticeWithAttachment(directoryDetail.getEmail(),
                                            noticeCreateDTO.getSubject(),
                                            noticeCreateDTO.getContent(),
                                            directoryDetail.getName() + ' ' + directoryDetail.getSurname(),
                                            resourceDoc.getFile());
                                    System.out.println(mail);
                                }
                            }
                        }
                    }
                }
                //if (noticeCreateDTO.isSms()) {
                //ToDo Sms bildirimleri bu alanda yapılacak
                //}
                //if (noticeCreateDTO.isApp()) {
                //ToDo Mobil uygulama bildirimleri bu alanda yapılacak
                //}
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

    //Notification Sayfası ilk açıldığında çalışan metot
    public Page<NoticeLog> getNotificationLogs(int page, int size) {

        try {
            int currentPage = page;
            int pageSize = size == 0 ? 5 : size;
            log.info("currentPage "+currentPage);
            log.info("pageSize "+pageSize);
            Pageable pageable = PageRequest.of(currentPage, pageSize);
            Page<NoticeLog> noticompanies = noticeLogRepository.getNotificationLogs(pageable);
               /*ObjectMapper mapper = new ObjectMapper();
               String jsonString = mapper
                       .writerWithDefaultPrettyPrinter()
                       .writeValueAsString(companies);
               log.info("jsonString "+jsonString);*/
            return noticompanies;
        }catch (Exception e){
            log.info("Error Message "+ e.getMessage());
        }
        return  null;
    }
    public Page<NoticeLog> getNotifications(int page, int size, String notificationType, String clientType, String search) {

        try {
            int currentPage = page;
            int pageSize = size == 0 ? 5 : size;
            log.info("currentPage "+currentPage);
            log.info("pageSize "+pageSize);
            Pageable pageable = PageRequest.of(currentPage, pageSize);
            Page<NoticeLog> noticompanies = noticeLogRepository.findAll(notificationType , clientType ,search , pageable);
               /*ObjectMapper mapper = new ObjectMapper();
               String jsonString = mapper
                       .writerWithDefaultPrettyPrinter()
                       .writeValueAsString(companies);
               log.info("jsonString "+jsonString);*/
            return noticompanies;
        }catch (Exception e){
            log.info("Error Message "+ e.getMessage());
        }
        return  null;
    }

    //Notification sayfasında filtreleme yapıldığında çalışan metot
    public Page<NoticeLog> getNotificationLogs(NotificationLogFilter notificationLogFilter) {

        try {

            int currentPage = notificationLogFilter.getPage();
            int pageSize = notificationLogFilter.getSize();
            log.info("currentPage "+currentPage);
            log.info("pageSize "+pageSize);

            LocalDate startDate = notificationLogFilter.getStartDate();
            LocalDate endDate = notificationLogFilter.getEndDate();
            String notificationType = notificationLogFilter.getNotificationType();
            String search = notificationLogFilter.getSearch().toLowerCase();
            Pageable pageable = PageRequest.of(currentPage, pageSize);

            Page<NoticeLog> noticompanieLogs = noticeLogRepository.getNotificationFilterLogs(search , notificationType, startDate , endDate , pageable);

               /*ObjectMapper mapper = new ObjectMapper();
               String jsonString = mapper
                       .writerWithDefaultPrettyPrinter()
                       .writeValueAsString(companies);
               log.info("jsonString "+jsonString);*/
            return noticompanieLogs;

        }catch (Exception e){
            log.info("Error Message "+ e.getMessage());
        }
        return  null;
    }

    //Notification Sayfasında bulunan büyütece tıkladığında id'ye göre sıralama işlemi yapan metot.
    public List<ClientDTO> getClientByNotifications(Long notiId){
        List<NotificationsUsers> notificationsUsers = notificationsUsersRepository.getClientByNotifications(notiId);
        List<Client> clientDTOS =  new ArrayList<>();
        try {
            for (NotificationsUsers notificationsUser: notificationsUsers) {
                if(notificationsUser.getClient() != null)
                System.out.println(notificationsUser.getClient().getCustomerClients().get(0).getCustomerInfo().getUser().getName());
                    clientDTOS.add(notificationsUser.getClient());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return clientMapper.toDto(clientDTOS);
    }
}
