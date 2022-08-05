package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.dto.BuyerDTO;
import com.tein.overcatchbackend.domain.dto.HelpDTO;
import com.tein.overcatchbackend.domain.dto.HelpTypeDTO;
import com.tein.overcatchbackend.domain.model.*;
import com.tein.overcatchbackend.enums.ModuleTypeEnum;
import com.tein.overcatchbackend.mapper.HelpMapper;
import com.tein.overcatchbackend.mapper.HelpTypeMapper;
import com.tein.overcatchbackend.repository.HelpRepository;
import com.tein.overcatchbackend.repository.HelpTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.List;

@Service
@AllArgsConstructor
public class HelpTypeService {

    private final HelpTypeRepository helpTypeRepository;
    private final HelpTypeMapper helpTypeMapper;


    public List<HelpType> getHelpTypes() {
        return helpTypeRepository.findAll();
    }


    public HelpTypeDTO getHelpTypeByID(long helpTypeID) {
        HelpType helpType = helpTypeRepository.findById(helpTypeID).get();
        HelpTypeDTO helpTypeDTO = helpTypeMapper.toDto(helpType);
        return helpTypeDTO;
    }

    public HelpType saveHelpTypeTopic(HelpType helpType) {
        helpTypeRepository.save(helpType);
        return helpType;
    }

}

