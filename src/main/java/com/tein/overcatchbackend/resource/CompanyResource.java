package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.ClientDTO;
import com.tein.overcatchbackend.domain.dto.CompanyDTO;
import com.tein.overcatchbackend.service.ClientService;
import com.tein.overcatchbackend.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyResource {

    private final CompanyService companyService;

    @RequestMapping(value = "/apply", method = RequestMethod.GET)
    public List<CompanyDTO> findByClientId(@RequestParam("clientId") String clientId) {
        return companyService.findByClientId(Long.valueOf(clientId));
    }
}
