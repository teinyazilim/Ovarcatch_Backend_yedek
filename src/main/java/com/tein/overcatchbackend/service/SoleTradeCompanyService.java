package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.model.FounderOwner;
import com.tein.overcatchbackend.repository.FounderOwnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SoleTradeCompanyService {

    private final FounderOwnerRepository founderOwnerRepository;


    public FounderOwner saveSole(FounderOwner founderOwner)
    { return founderOwnerRepository.save(founderOwner);
    }


}

