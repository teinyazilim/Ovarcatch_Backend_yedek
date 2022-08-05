package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.UserTransactionDTO;
import com.tein.overcatchbackend.domain.model.UserTransaction;
import com.tein.overcatchbackend.mapper.UserTransactionMapper;
import com.tein.overcatchbackend.service.UserTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class UserTransactionResource {


    private final UserTransactionService userTransactionService;
    private final UserTransactionMapper userTransactionMapper;

    @RequestMapping(value = "/usertransac", method = RequestMethod.POST)
    public UserTransaction saveUserTransac(@RequestBody UserTransactionDTO userTransactionDTO) {
        UserTransaction userTransaction = userTransactionMapper.toEntity(userTransactionDTO);
        return userTransactionService.saveUserTransac(userTransaction);
    }

}
