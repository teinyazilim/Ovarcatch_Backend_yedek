package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.LetterDTO;
import com.tein.overcatchbackend.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class MailResource {
    private final MailService mailService;
    @RequestMapping(value = "/invoice", method = RequestMethod.POST)
    public void sendMessageWithAttachment(@RequestParam("to") String to,
                                          @RequestParam(required = false, name="subject") String subject,
                                          @RequestParam(required = false, name="clientName") String clientName,
                                          @RequestParam("pathToAttachment") String pathToAttachment) throws MessagingException {
//        mailService.sendMessageWithAttachment(to,subject,clientName,pathToAttachment);
    }
}
