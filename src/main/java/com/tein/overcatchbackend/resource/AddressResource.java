package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.AddressDTO;
import com.tein.overcatchbackend.domain.model.Address;
import com.tein.overcatchbackend.domain.model.AddressNew;
import com.tein.overcatchbackend.mapper.AddressMapper;
import com.tein.overcatchbackend.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressResource {


    private final AddressService addressService;
    private final AddressMapper addressMapper;

    @RequestMapping(value = "/addresss", method = RequestMethod.POST)
    public Address saveAddress(@RequestBody AddressDTO addressDTO) {
        Address address= addressMapper.toEntity(addressDTO);
        return  addressService.saveAddress(address);
    }
    //Aşağıdaki kodu yeni ekledim.
    @RequestMapping(value = "/addressList", method = RequestMethod.GET)
    public List<AddressNew> getNewAddressList(@RequestParam("clientId") String clientId) {
        return addressService.getNewAddressList(Long.valueOf(clientId));
    }
    // Yukarıdaki Kodu yeni ekledim.
}
