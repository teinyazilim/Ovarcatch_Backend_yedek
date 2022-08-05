package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.CompanyDTO;
import com.tein.overcatchbackend.domain.model.FounderOwner;
import com.tein.overcatchbackend.service.CompanyService;
import com.tein.overcatchbackend.service.FounderOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/founderOwner")
@RequiredArgsConstructor
public class FounderOwnerResource {
    private final FounderOwnerService founderOwnerService;

    @RequestMapping(value = "/apply", method = RequestMethod.GET)
    public List<FounderOwner> findByClientId(@RequestParam("clientId") String clientId) {
        return founderOwnerService.findByClientId(Long.valueOf(clientId));
    }
}
