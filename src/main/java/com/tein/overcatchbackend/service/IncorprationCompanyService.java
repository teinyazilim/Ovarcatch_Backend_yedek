package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.model.Company;
import com.tein.overcatchbackend.repository.IncorprationCompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IncorprationCompanyService {

    private final IncorprationCompanyRepository incorprationCompanyRepository;


    public Company saveIncorpration(Company company)
    { return incorprationCompanyRepository.save(company);
    }


}

