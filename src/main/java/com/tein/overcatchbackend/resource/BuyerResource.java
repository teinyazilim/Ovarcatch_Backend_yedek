package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.BuyerDTO;
import com.tein.overcatchbackend.service.BuyerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buyer")
@RequiredArgsConstructor
public class BuyerResource {

    private final BuyerService buyerService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BuyerDTO saveBuyer(@RequestBody BuyerDTO buyerDTO) {
        return buyerService.saveBuyerDetail(buyerDTO);
    }

    @RequestMapping(value = "/allbyclientid", method = RequestMethod.GET)
    public List<BuyerDTO> getAllByClientId(@RequestParam("clientId") String clientId) {
        return buyerService.getAllBuyerByClientId(Long.valueOf(clientId));
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public BuyerDTO getBuyerByClient(@RequestParam("buyerId") String buyerId) {
        return buyerService.getBuyerDetail(Long.valueOf(buyerId));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<?> deleteBuyerById(@RequestParam("buyerId") String buyerId) {
        try {
            buyerService.deleteBuyerById(Long.valueOf(buyerId));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }
    @RequestMapping(value = "/controlForDeleteCustomer", method = RequestMethod.GET)
    public Integer controlForDeleteCustomer(@RequestParam("client_id") String client_id,
                                        @RequestParam("buyer_name") String buyer_name){
        return buyerService.controlForDeleteCustomer(Long.parseLong(client_id), buyer_name) ;
    }
}



