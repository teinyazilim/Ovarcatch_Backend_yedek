package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.dto.exception.AppException;
import com.tein.overcatchbackend.domain.model.User;
import com.tein.overcatchbackend.repository.UserRepository;
import com.tein.overcatchbackend.security.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    public User getCurrentUser(){
        String email= SecurityUtils.getCurrentUserLogin().orElseThrow(()->new AppException("User not found"));
        return userRepository.findByEmail(email);
    }
}
