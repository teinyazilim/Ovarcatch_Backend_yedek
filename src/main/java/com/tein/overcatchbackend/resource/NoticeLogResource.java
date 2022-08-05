package com.tein.overcatchbackend.resource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tein.overcatchbackend.domain.dto.HelpDTO;
import com.tein.overcatchbackend.domain.dto.NoticeCreateDTO;
import com.tein.overcatchbackend.domain.dto.ClientDTO;
import com.tein.overcatchbackend.domain.dto.NoticeLogDTO;
import com.tein.overcatchbackend.domain.model.*;
import com.tein.overcatchbackend.mapper.NoticeLogCreateDTOMapper;
import com.tein.overcatchbackend.service.FileStorageService;
import com.tein.overcatchbackend.service.NoticeService;
import com.tein.overcatchbackend.mapper.ClientMapper;
import com.tein.overcatchbackend.mapper.NoticeLogMapper;
import com.tein.overcatchbackend.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeLogResource {

    private final NoticeService noticeService;
    private final NoticeLogMapper noticeLogMapper;
    private final ClientService clientService;
    private final FileStorageService fileStorageService;
    private final NoticeLogCreateDTOMapper noticeLogCreateDTOMapper;

    @RequestMapping(value = "/getNotices", method = RequestMethod.GET)
    public Page<NoticeLogDTO> getNotice(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "0") int size,
                                        @RequestParam String notificationType,
                                        @RequestParam String clientType,
                                        @RequestParam String search) {

        Page<NoticeLog> noticompanies = noticeService.getNotifications(page, size, notificationType, clientType, search);
        List<NoticeLogDTO> asd = noticeLogMapper.toDto(noticompanies.toList());

        //Pageable paging = PageRequest.of(page, size);
        Page<NoticeLogDTO> responseNotices = new PageImpl<>(asd, noticompanies.getPageable(), noticompanies.getTotalElements()); // Şu kodu sor

        return responseNotices;
    }

    //Notification Sayfasında Filtreleme yapıdığında
    @RequestMapping(value = "/getNoticeLogFilters", method = RequestMethod.POST)
    public Page<NoticeLogDTO> getNoticesLog(@RequestBody NotificationLogFilter notificationLogFilter) {

        Page<NoticeLog> noticompanies = noticeService.getNotificationLogs(notificationLogFilter);
        List<NoticeLogDTO> asd = noticeLogMapper.toDto(noticompanies.toList());

        //Pageable paging = PageRequest.of(page, size);
        Page<NoticeLogDTO> responseNotices = new PageImpl<>(asd, noticompanies.getPageable(), noticompanies.getTotalElements()); // Şu kodu sor

        return responseNotices;
    }

    // Notification sayfası ilk açıldığında çalışacak kod
    @RequestMapping(value = "/getNoticesLog", method = RequestMethod.GET)
    public Page<NoticeLogDTO> getNoticeLogs(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "0") int size) {

        Page<NoticeLog> noticompanies = noticeService.getNotificationLogs(page , size);
        List<NoticeLogDTO> asd = noticeLogMapper.toDto(noticompanies.toList());

        //Pageable paging = PageRequest.of(page, size);
        Page<NoticeLogDTO> responseNotices = new PageImpl<>(asd, noticompanies.getPageable(), noticompanies.getTotalElements()); // Şu kodu sor

        return responseNotices;
    }

    // Notification sayfası ilk açıldığında çalışacak kod
    @RequestMapping(value = "/getClientByNotifications", method = RequestMethod.GET)
    public List<ClientDTO> getClientByNotifications(   @RequestParam Long notiId) {

        //List<ClientDTO> clientDTONotifList = clientService.getClientByNotifications(subject , message);
        List<ClientDTO> clientDTONotifList = noticeService.getClientByNotifications(notiId);
        return clientDTONotifList;
    }

    // NotificationCreate Sayfasında List Butonuna tıklandığında seçili olan kişilere mail atan kısım ek dosyası ile .
    // NotificatinCreate sayfasından notice_log tablosuna submit - kayıt atıyor ...
    //Dosyalı veya dosyasız olarak mail gönderebiliyor ve diske kayıt yapabiliyor ...
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void createNotificationWithFile(
                                            @RequestParam(required = false, name = "file") MultipartFile file,
                                            @RequestParam String notificationCreateDTO   ) throws Exception {

        NoticeCreateDTO noticeCreateDTO = new ObjectMapper().readValue(notificationCreateDTO, NoticeCreateDTO.class);
        //NoticeLog noticeLogModel = noticeLogCreateDTOMapper.toEntity(noticeCreateDTO);

        System.out.println(noticeCreateDTO.getContent());
        System.out.println(noticeCreateDTO.getSubject());
        System.out.println(noticeCreateDTO.isMail());
        try{
                noticeService.sender(noticeCreateDTO , file);
                }
        catch (Exception e){
                e.printStackTrace();
        }
    }
    //Notification sayfasına giriş yaptığında resend ettiğinde çalışacak olan metot ...
    @PostMapping("/resendNotification")
    public void resendNotification( @RequestBody NoticeCreateDTO noticeCreateDTO) throws Exception {
        try{
            noticeService.resendNotification(noticeCreateDTO);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    /*
    @PostMapping("/create")
    public void create( @RequestBody NoticeCreateDTO noticeCreateDTO) throws Exception {
        try{
            //noticeService.sender(noticeCreateDTO);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
     */

    //    @RequestMapping(value = "/getNoticeLogFiltersOfSearch", method = RequestMethod.GET)
    //    public List<ClientDTO> getNoticeLogFiltersOfSearch(  @RequestParam String search,
    //                                                         @RequestParam String subject,
    //                                                         @RequestParam String message) {
    //
    //        List<ClientDTO> clientDTONotifList = clientService.getNoticeLogFiltersOfSearch(search ,subject , message);
    //        return clientDTONotifList;
    //    }
}