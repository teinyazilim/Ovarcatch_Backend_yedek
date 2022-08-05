package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.dto.CompanyDTO;
import com.tein.overcatchbackend.domain.model.Client;
import com.tein.overcatchbackend.repository.ClientRepository;
import com.tein.overcatchbackend.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    public List<CompanyDTO> findByClientId(Long clientId){
        List<CompanyDTO> companies = companyRepository.findByClientId(clientId);

        return companies;
    }
}
