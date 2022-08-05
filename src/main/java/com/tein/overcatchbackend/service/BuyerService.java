package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.dto.BuyerDTO;
import com.tein.overcatchbackend.domain.model.Buyer;
import com.tein.overcatchbackend.mapper.BuyerMapper;
import com.tein.overcatchbackend.repository.BuyerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BuyerService {

    private final BuyerRepository buyerRepository;
    private final BuyerMapper buyerMapper;
    private final CurrentUserService currentUserService;

    public BuyerDTO saveBuyerDetail(BuyerDTO buyerDTO)
    {
        Buyer buyer = buyerMapper.toEntity(buyerDTO);
        buyer.setIsActive(true);
        return buyerMapper.toDto(buyerRepository.save(buyer));
    }

    public List<BuyerDTO> getAllBuyerByClientId(Long clientId)
    {
        return buyerMapper.toDto(buyerRepository.findAllByClientId(clientId));
    }

    public BuyerDTO getBuyerDetail(Long id){
        return buyerMapper.toDto(buyerRepository.findById(id).get());
    }

    public ResponseEntity<?> deleteBuyerById(Long id){
        buyerRepository.deleteBuyerById(id);
        return ResponseEntity.ok("Success");
    }
    public Integer controlForDeleteCustomer(Long client_id, String buyer_name){
        int i = buyerRepository.controlForDeleteCustomer(client_id,buyer_name);
        return i;
    }
}


