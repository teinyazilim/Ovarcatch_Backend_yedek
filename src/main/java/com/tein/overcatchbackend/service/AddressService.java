package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.model.Address;
import com.tein.overcatchbackend.domain.model.AddressNew;
import com.tein.overcatchbackend.repository.AddressNewRepository;
import com.tein.overcatchbackend.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressNewRepository addressNewRepository;

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }
    //Aşağıdaki kodu yeni ekledim
    public List<AddressNew> getNewAddressList(Long clientId) {
       return addressNewRepository.getNewAddressList(clientId);
    }
    //Yukarıdaki kodu yeni ekledim
}

