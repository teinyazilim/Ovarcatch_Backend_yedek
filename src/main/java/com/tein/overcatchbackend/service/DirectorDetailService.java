package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.model.DirectorDetail;
import com.tein.overcatchbackend.repository.DirectorDetailRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DirectorDetailService {

    private final DirectorDetailRepository directorDetailRepository;

    public DirectorDetail saveDirectorDetail(DirectorDetail directorDetail)
    { return directorDetailRepository.save(directorDetail);
    }

    public DirectorDetail findById(Long id){
//        if(directorDetailRepository.findById(id) instanceof Object){
//            DirectorDetail directorDetail =(DirectorDetail) directorDetailRepository.findById(id);
//        }
        DirectorDetail directorDetail = directorDetailRepository.findByDirectorId(id);
        return directorDetail;
    }
}

