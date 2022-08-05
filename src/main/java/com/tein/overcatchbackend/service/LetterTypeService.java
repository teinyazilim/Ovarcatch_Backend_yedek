package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.dto.ClientDTO;
import com.tein.overcatchbackend.domain.dto.LetterTypeDTO;
import com.tein.overcatchbackend.domain.model.Client;
import com.tein.overcatchbackend.domain.model.Letter;
import com.tein.overcatchbackend.domain.model.LetterType;
import com.tein.overcatchbackend.domain.model.User;
import com.tein.overcatchbackend.enums.UserType;
import com.tein.overcatchbackend.mapper.LetterTypeMapper;
import com.tein.overcatchbackend.repository.LetterTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LetterTypeService {

    private final LetterTypeRepository letterTypeRepository;
    private final CurrentUserService currentUserService;
    private final LetterTypeMapper letterTypeMapper;


    public LetterType saveLetterType(LetterType letterType) {
        if(!(letterType.getUserRole().indexOf("MANAGER") > -1))
            letterType.setUserRole(letterType.getUserRole() + "," + "MANAGER");
        return letterTypeRepository.save(letterType);
    }

    public List<LetterTypeDTO> getAllLetterTypes() {
        return letterTypeMapper.toDto(letterTypeRepository.findAllLetter(currentUserService.getCurrentUser().getUserType().toString()));
    }

    public List<LetterTypeDTO> getAllUsed() {
        List<LetterType> letterTypes = letterTypeRepository.getAllUsed();
        return letterTypeMapper.toDto(letterTypes);
    }

    public List<LetterTypeDTO> getLetterTypesByClientType(String clientType) {

        User currentUser = currentUserService.getCurrentUser();
        String userRole = currentUser.getUserType() == UserType.CUSTOMER ? "CLIENT" : currentUser.getUserType().toString();
        return letterTypeMapper.toDto(letterTypeRepository.getLetterTypeByClientTypeAndAndUserRole(clientType, userRole));
    }

    public LetterTypeDTO getLetterTypeByID(long letterTypeID) {
        LetterType letterType = letterTypeRepository.findById(letterTypeID).get();
        LetterTypeDTO letterTypeDTO = letterTypeMapper.toDto(letterType);

        return letterTypeDTO;
    }

    public ResponseEntity<?>  deleteLetter(Long id){
        LetterType letterType = letterTypeRepository.findById(id).get();
        letterType.setIsActive(false);
        letterTypeRepository.save(letterType);
        return ResponseEntity.ok("Success");
    }


}

