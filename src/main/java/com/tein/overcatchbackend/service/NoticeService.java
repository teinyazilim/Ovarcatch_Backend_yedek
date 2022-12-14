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

    //Notification-Create Sayfas??nda yeni bir bildirim olu??turmak istedi??inde ??al????an metot
    //Bu metot ilgili bildirimi notice_log tablosuna kay??t yap??yor...
    //Notication klas??r??ne kay??t yap??yor ...
    //Ve ilgili ki??ilere mail g??nderiyor ...
    @JsonProperty("noticeCreateDTO")
    public ResponseEntity<?> sender(NoticeCreateDTO noticeCreateDTO , MultipartFile file) {
        try {

            //Gelen Data Bilgileri Notice_log Tablosuna Kaydedildi .
            NoticeLog noticeLog = new NoticeLog();
            noticeLog.setNotiType("Mail");
            noticeLog.setSubject(noticeCreateDTO.getSubject());
            noticeLog.setMessage(noticeCreateDTO.getContent());
            if ( file != null){
                //E??er Eklenen bildirim dosyal?? ise bunu diske kaydediyorum ...
                Document document = fileStorageService.loadFilefromNotification(file);
                noticeLog.setDocument(document);
            }
            NoticeLog finalNoticeLog =  noticeLogRepository.save(noticeLog);
            //Yukar??daki kodda Notice_log Tablosuna kaydetti??im verinin id bilgisini
            //NoticeUsers tablosuna id'sini setlemek i??in finalNoticeLog de??i??kenine
            //Setliyorum ard??ndan notificationUsers objesine veriyi setliyorum .
            if ( file == null){
                // Dosyas??z email g??nderme ve db ye kaydetme i??lemi .
                //List<Client> clientList = clientMapper.toEntity(noticeCreateDTO.getClientList());
                List<ClientDTO> clientList = noticeCreateDTO.getClientList();
                for (ClientDTO client : clientList) {

                    //E??er Kullan??cn??n user_language k??sm?? null ise default olarak Ingilizce olan template g??nderiyorum ...
                    if(client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage() == null){
                        client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().setUserlanguage("en");
                    }

                    if (client.getFounderOwner() != null && client.getFounderOwner().getEmail() != null){
                        //Company K??sm?? Null
                        if (noticeCreateDTO.isMail() && client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage().equals("tr"))
                        {
                            //T??rk??e i??erikli Template g??nderilme i??lemi
                            //Dosyas??z - email g??nderme i??lemi
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
                            //Inglizce i??erikli Template g??nderilme i??lemi
                            //Dosyas??z - email g??nderme i??lemi
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
                        // FounderOwer K??sm?? null
                        // Ve ben directory email g??nderece??im
                        if (noticeCreateDTO.isMail() && client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage().equals("tr")) {
                            for (DirectorDetailDTO directoryDetail: client.getCompany().getDirectorDetails()
                            ) {
                                if (directoryDetail.getEmail() != null){

                                    System.out.println(directoryDetail.getEmail());
                                    System.out.println(noticeCreateDTO.getSubject());
                                    System.out.println(noticeCreateDTO.getContent());
                                    System.out.println(directoryDetail.getName());
                                    // T??rk??e Mail Se??ene??i eklenecek !
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
                                    // Ingilizce i??erikli Template g??nderme i??lemi ...
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
                            //ToDo Sms bildirimleri bu alanda yap??lacak
                            //}
                            //if (noticeCreateDTO.isApp()) {
                            //ToDo Mobil uygulama bildirimleri bu alanda yap??lacak
                            //}
                }
            }
            else {
                // Dosyal?? email g??nder ve ve disk & DB ' ye veri kaydetme i??lemi .
                System.out.println(file.getName());
                //List<Client> clientList = clientMapper.toEntity(noticeCreateDTO.getClientList());
                List<ClientDTO> clientList = noticeCreateDTO.getClientList();
                for (ClientDTO client : clientList) {
                    //E??er Kullan??cn??n user_language k??sm?? null ise default olarak Ingilizce olan template g??nderiyorum ...
                    if(client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage() == null){
                        client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().setUserlanguage("en");
                    }
                    if (client.getFounderOwner() != null && client.getFounderOwner().getEmail() != null){
                        //Company K??sm?? Null
                        if ( noticeCreateDTO.isMail() && client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage().equals("tr")) {
                            // Dosya boyutu Max. MG fazla olamayacak bu kontrolu sa??la ....
                            // T??rk??e Mail g??nderme i??lemi ...
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
                            // Dosya boyutu Max. MG fazla olamayacak bu kontrolu sa??la ....
                            // Inglizce Mail g??nderme i??lemi ...
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
                        // FounderOwer K??sm?? null
                        // Ve ben directory email g??nderece??im
                        if (noticeCreateDTO.isMail() && client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage().equals("tr")) {
                            for (DirectorDetailDTO directoryDetail: client.getCompany().getDirectorDetails()
                            ) {
                                if (directoryDetail.getEmail() != null){
                                    //T??rk??e Mail g??nderme i??lemi ...
                                    System.out.println(directoryDetail.getEmail());
                                    System.out.println(noticeCreateDTO.getSubject());
                                    System.out.println(noticeCreateDTO.getContent());
                                    System.out.println(directoryDetail.getName());

                                    // Dosya boyutu Max. 20 MB fazla olamayacak bu kontrolu sa??la ....
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
                                    //Ingilizce Mail g??nderme i??lemi ...
                                    System.out.println(directoryDetail.getEmail());
                                    System.out.println(noticeCreateDTO.getSubject());
                                    System.out.println(noticeCreateDTO.getContent());
                                    System.out.println(directoryDetail.getName());

                                    // Dosya boyutu Max. 20 MB fazla olamayacak bu kontrolu sa??la ....
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
                            //ToDo Sms bildirimleri bu alanda yap??lacak
                            //}
                            //if (noticeCreateDTO.isApp()) {
                            //ToDo Mobil uygulama bildirimleri bu alanda yap??lacak
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

    //Notification sayfas?? resend edildi??in de tetiklenen metot ...
    @JsonProperty("noticeCreateDTO")
    public ResponseEntity<?> resendNotification(NoticeCreateDTO noticeCreateDTO ) {
        try {
            Resource resourceDoc = null;
            //A??a????daki kodda notification_log tablosunda bulunan document_id sayesinde
            //istedi??im Id'ye ba??l?? olarak bilgileri getirebiliyorum dolay??s??yla bana
            // Dok??man??n bilgiside gelmi?? oluyor .
            NoticeLog noticeLogDoc = noticeLogRepository.findById(noticeCreateDTO.getId()).get();
            //List<Client> clientList = clientMapper.toEntity(getClientByNotifications(noticeCreateDTO.getId()));
            List<ClientDTO> clientList = noticeCreateDTO.getClientList();
            if (noticeLogDoc.getDocument() != null)
                resourceDoc = fileStorageService.loadFileAsResource(noticeLogDoc.getDocument().getFileName(),noticeLogDoc.getDocument().getFilePath());
            System.out.println(resourceDoc);
            for (ClientDTO client : clientList) {

                //E??er Kullan??cn??n user_language k??sm?? null ise default olarak Ingilizce olan template g??nderiyorum ...
                if(client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage() == null){
                    client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().setUserlanguage("en");
                }

                if (client.getFounderOwner() != null && client.getFounderOwner().getEmail() != null){
                    //Company K??sm?? Null Ve ben founder email g??nderece??im
                    if (noticeCreateDTO.isMail() && client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage().equals("tr")) {
                        //E??er Resend edildi??inde d??k??man?? yok ise d??k??mans??z email g??nderece??im
                        //Resend edilecek kullan??c??n??n user_language k??s??m?? 'tr' ise ilgili ??ekilde mail g??nderilecek ...
                        if( noticeLogDoc.getDocument() == null){
                            String mail = mailService.sendNoticeMailHtmlTR(
                                                                client.getFounderOwner().getEmail() ,
                                                                noticeCreateDTO.getSubject() ,
                                                                noticeCreateDTO.getContent() ,
                                                                client.getFounderOwner().getName() + ' ' +client.getFounderOwner().getSurname());
                            System.out.println(mail);
                        }
                        else if (noticeLogDoc.getDocument() != null){
                            //E??er resend edilecek veride d??k??man varsa email d??k??manl?? g??nderilecek .
                            System.out.println("D??k??manl?? Veri Resend edildi .");
                            //Bu k??s??m d??zeltilecek....
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
                        //E??er Resend edildi??inde d??k??man?? yok ise d??k??mans??z email g??nderece??im
                        //Resend edilecek kullan??c??n??n user_language k??s??m?? 'tr' ise ilgili ??ekilde mail g??nderilecek ...
                        if( noticeLogDoc.getDocument() == null){
                            String mail = mailService.sendNoticeMailHtml(
                                    client.getFounderOwner().getEmail() ,
                                    noticeCreateDTO.getSubject() ,
                                    noticeCreateDTO.getContent() ,
                                    client.getFounderOwner().getName() + ' ' +client.getFounderOwner().getSurname());
                            System.out.println(mail);
                        }
                        else if (noticeLogDoc.getDocument() != null){
                            //E??er resend edilecek veride d??k??man varsa email d??k??manl?? g??nderilecek .
                            System.out.println("D??k??manl?? Veri Resend edildi .");
                            //Bu k??s??m d??zeltilecek....
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
                    // FounderOwer K??sm?? null Ve ben directory email g??nderece??im
                    if (noticeCreateDTO.isMail() && client.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage().equals("tr")) {
                        // T??rk??e Mail G??nderece??im
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
                                    //E??er resend edilecek veride d??k??man varsa email d??k??manl?? g??nderilecek .
                                    System.out.println("D??k??manl?? veri resend edildi .");
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
                                    //E??er resend edilecek veride d??k??man varsa email d??k??manl?? g??nderilecek .
                                    System.out.println("D??k??manl?? veri resend edildi .");
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
                //ToDo Sms bildirimleri bu alanda yap??lacak
                //}
                //if (noticeCreateDTO.isApp()) {
                //ToDo Mobil uygulama bildirimleri bu alanda yap??lacak
                //}
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

    //Notification Sayfas?? ilk a????ld??????nda ??al????an metot
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

    //Notification sayfas??nda filtreleme yap??ld??????nda ??al????an metot
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

    //Notification Sayfas??nda bulunan b??y??tece t??klad??????nda id'ye g??re s??ralama i??lemi yapan metot.
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
