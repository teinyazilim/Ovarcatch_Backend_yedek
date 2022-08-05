package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.DirectorDetailDTO;
import com.tein.overcatchbackend.domain.model.DirectorDetail;
import com.tein.overcatchbackend.mapper.DirectorDetailMapper;
import com.tein.overcatchbackend.service.DirectorDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class DirectorDetailResource {


    private final DirectorDetailService directorDetailService;
    private final DirectorDetailMapper directorDetailMapper;

    @RequestMapping(value = "/director", method = RequestMethod.POST)
    public DirectorDetail saveDirectorDetail(@RequestBody DirectorDetailDTO directorDetailDTO) {
        DirectorDetail directorDetail = directorDetailMapper.toEntity(directorDetailDTO);
        return directorDetailService.saveDirectorDetail(directorDetail);
    }

    @RequestMapping(value = "/findById", method = RequestMethod.POST)
    public DirectorDetail findById(@RequestParam("directorId") String directorId){
        DirectorDetail directorDetail = null;
        if(directorId.equals("undefined")){

        }else{
            directorDetail = directorDetailService.findById(Long.parseLong(directorId));
        }
        return directorDetail;
    }

}
