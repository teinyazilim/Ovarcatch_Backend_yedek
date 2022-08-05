package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.FounderOwnerDTO;
import com.tein.overcatchbackend.domain.model.FounderOwner;
import com.tein.overcatchbackend.mapper.FounderOwnerMapper;
import com.tein.overcatchbackend.service.SoleTradeCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class SoleTradeCompanyResource {


    private final SoleTradeCompanyService soleTradeCompanyService;
    private final FounderOwnerMapper founderOwnerMapper;

    @RequestMapping(value = "/sole", method = RequestMethod.POST)
    public FounderOwner saveSole(@RequestBody FounderOwnerDTO founderOwnerDTO) {
        FounderOwner founderOwner = founderOwnerMapper.toEntity(founderOwnerDTO);
        return soleTradeCompanyService.saveSole(founderOwner);
    }

}
