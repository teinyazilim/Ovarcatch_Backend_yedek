package com.tein.overcatchbackend.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tein.overcatchbackend.domain.dto.CustomerClientDTO;
import com.tein.overcatchbackend.domain.dto.HelpDTO;
import com.tein.overcatchbackend.domain.dto.UserDTO;
import com.tein.overcatchbackend.domain.model.Document;
import com.tein.overcatchbackend.domain.model.Help;
import com.tein.overcatchbackend.domain.model.User;
import com.tein.overcatchbackend.mapper.HelpMapper;
import com.tein.overcatchbackend.mapper.UserMapper;
import com.tein.overcatchbackend.repository.UserRepository;
import com.tein.overcatchbackend.service.CurrentUserService;
import com.tein.overcatchbackend.service.FileStorageService;
import com.tein.overcatchbackend.service.HelpService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/help")
@RequiredArgsConstructor
public class HelpResource {


    private final HelpMapper helpMapper;
    private final HelpService helpService;
    private final FileStorageService fileStorageService;
    private final CurrentUserService currentUserService;

//    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    public HelpDTO saveLetter(@RequestBody HelpDTO helpDTO) {
//        Help help = helpMapper.toEntity(helpDTO);
//        return helpMapper.toDto(helpService.saveHelp(help));
//    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<HelpDTO> getHelps() {
        return helpService.getHelps();
    }

    @RequestMapping(value = "/getbyid", method = RequestMethod.GET)
    public HelpDTO getHelpDetail(@RequestParam("helpID") String helpID) {
        return helpService.getHelpByID(Long.valueOf(helpID));
    }

    @RequestMapping(value = "/getbytaskid", method = RequestMethod.GET)
    public HelpDTO getHelpDetailByTask(@RequestParam("taskID") String taskID) {
        return helpService.getHelpByTaskId(Long.valueOf(taskID));
    }

    @RequestMapping(value = "/gethelpbyclientid", method = RequestMethod.GET)
    public List<HelpDTO> getHelpsByClient(@RequestParam("clientId") String clientId) {
        return helpService.getHelpsByClientId(Long.valueOf(clientId));
    }

    @RequestMapping(value = "/getSupports", method = RequestMethod.GET)
    public List<HelpDTO> getSupportList( @RequestParam String search) {
        return helpService.getSupportList(search);
    }

    // SupportTaks Sayfsında Search || SupportType || StatusType Tıklandığında çalışacak olan fitreleme ...
    @RequestMapping(value = "/getSupportsFilter", method = RequestMethod.GET)
    public List<HelpDTO> getSupportsFilter( @RequestParam String search ,
                                            @RequestParam String supportType ,
                                            @RequestParam String statusType) {

        return helpService.getSupportListFilter(search , supportType, statusType);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public HelpDTO saveLetter(@RequestParam(required = false, name = "file") MultipartFile file, @RequestParam String helpDTO) throws Exception {


        HelpDTO helpDTO1 = new ObjectMapper().readValue(helpDTO, HelpDTO.class);
        Help help = helpMapper.toEntity(helpDTO1);
        User user = currentUserService.getCurrentUser();
        help.setRequest_user(user);

        if (file != null) {
            Document document = fileStorageService.loadFilefromHelp(helpDTO1, file);
            help.setDocument(document);
        }

        return helpMapper.toDto(helpService.sendSupportTicket(help, file));
    }

    @RequestMapping(value = "/answeredTicket", method = RequestMethod.POST)
    public void responsiveTicket( @RequestParam String helpDTO) throws Exception {

        HelpDTO helpDTO1 = new ObjectMapper().readValue(helpDTO, HelpDTO.class);
        Help help = helpMapper.toEntity(helpDTO1);

        User user = currentUserService.getCurrentUser();
        help.setSupport_user(user);
        helpService.answerTicket(help);
    }

    //    @RequestMapping(value = "/answeredTicket", method = RequestMethod.POST)
    //    public HelpDTO responsiveTicket(@RequestParam(required = false, name = "file")
    //                                             MultipartFile file, @RequestParam String helpDTO) throws Exception {
    //
    //        HelpDTO helpDTO1 = new ObjectMapper().readValue(helpDTO, HelpDTO.class);
    //        Help help = helpMapper.toEntity(helpDTO1);
    //
    //        User user = currentUserService.getCurrentUser();
    //        help.setSupport_user(user);
    //
    //        if (file != null) {
    //            Document document = fileStorageService.loadFilefromHelp(helpDTO1, file);
    //            help.setDocument(document);
    //        }
    //
    //        return helpMapper.toDto(helpService.answerTicket(help));
    //    }
}
