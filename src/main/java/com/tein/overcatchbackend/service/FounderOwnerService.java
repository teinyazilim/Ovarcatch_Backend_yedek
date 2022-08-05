package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.model.FounderOwner;
import com.tein.overcatchbackend.repository.FounderOwnerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FounderOwnerService {
    private final FounderOwnerRepository founderOwnerRepository;
    public List<FounderOwner> findByClientId(Long clientId) {
        List<FounderOwner> founderOwners = founderOwnerRepository.findByClientId(clientId);

        return founderOwners;
    }
}
