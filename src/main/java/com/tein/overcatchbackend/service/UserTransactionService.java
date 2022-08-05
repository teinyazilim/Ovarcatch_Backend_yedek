package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.model.UserTransaction;
import com.tein.overcatchbackend.repository.UserTransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserTransactionService {

    private final UserTransactionRepository userTransactionRepository;


    public UserTransaction saveUserTransac(UserTransaction userTransaction)
    { return userTransactionRepository.save(userTransaction);
    }


}

