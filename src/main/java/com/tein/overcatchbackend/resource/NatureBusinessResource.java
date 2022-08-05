package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.NatureBusinessDTO;
import com.tein.overcatchbackend.domain.model.NatureBusiness;
import com.tein.overcatchbackend.mapper.NatureBusinessMapper;
import com.tein.overcatchbackend.service.NatureBusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class NatureBusinessResource {


    private final NatureBusinessService natureBusinessService;
    private final NatureBusinessMapper natureBusinessMapper;

    @RequestMapping(value = "/nature", method = RequestMethod.POST)
    public NatureBusiness saveNatureBusiness(@RequestBody NatureBusinessDTO natureBusinessDTO) {
        NatureBusiness natureBusiness= natureBusinessMapper.toEntity(natureBusinessDTO);
        return natureBusinessService.saveNatureBusiness(natureBusiness);
    }

    @RequestMapping(value = "/naturebusiness", method = RequestMethod.GET)
    public List<NatureBusinessDTO> naturesBusiness() {
        return natureBusinessService.getNatures();
    }

}
