package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.dto.NatureBusinessDTO;
import com.tein.overcatchbackend.domain.dto.UserDTO;
import com.tein.overcatchbackend.domain.model.NatureBusiness;
import com.tein.overcatchbackend.domain.model.User;
import com.tein.overcatchbackend.mapper.NatureBusinessMapper;
import com.tein.overcatchbackend.repository.NatureBusinessRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NatureBusinessService {

    private final NatureBusinessRepository natureBusinessRepository;
    private final NatureBusinessMapper natureBusinessMapper;


    public NatureBusiness saveNatureBusiness(NatureBusiness natureBusiness)
    { return natureBusinessRepository.save(natureBusiness);
    }

    public List<NatureBusinessDTO> getNatures()
    {


        List<NatureBusiness> user=natureBusinessRepository.findAll();
        List<NatureBusinessDTO> userDTO=natureBusinessMapper.toDto(user);
        for(int i=0;i<user.size();i++){
            userDTO.get(i).setId(user.get(i).getId());
        }


        return userDTO;

    }


}

