package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.BankTransactionDTO;
import com.tein.overcatchbackend.domain.dto.BankTransactionDetailDTO;
import com.tein.overcatchbackend.domain.model.BankTransactionDetail;
import com.tein.overcatchbackend.mapper.BankTransactionDetailMapper;
import com.tein.overcatchbackend.service.BankTransactionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company/")
@RequiredArgsConstructor
public class BankTransactionDetailResource {


    private final BankTransactionDetailService bankTransactionDetailService;
    private final BankTransactionDetailMapper bankTransactionDetailMapper;

    @RequestMapping(value = "/transactiondetail", method = RequestMethod.POST)
    public BankTransactionDetail saveBankTransactionDetail(@RequestBody BankTransactionDetailDTO bankTransactionDetailDTO) {
        BankTransactionDetail bankTransactionDetail= bankTransactionDetailMapper.toEntity(bankTransactionDetailDTO);
        return bankTransactionDetailService.saveBankTransactionDetail(bankTransactionDetail);
    }
    @RequestMapping(value = "/gettransactiondetailbyid", method= RequestMethod.GET)
    public List<BankTransactionDetailDTO> get(@RequestParam("transactionId") String transactionId) {
        return bankTransactionDetailService.getTransactionById(Long.valueOf(transactionId));
    }

}
