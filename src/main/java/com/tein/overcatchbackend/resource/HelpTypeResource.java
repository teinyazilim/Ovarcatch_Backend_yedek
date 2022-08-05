package com.tein.overcatchbackend.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tein.overcatchbackend.domain.dto.HelpDTO;
import com.tein.overcatchbackend.domain.dto.HelpTypeDTO;
import com.tein.overcatchbackend.domain.model.Document;
import com.tein.overcatchbackend.domain.model.Help;
import com.tein.overcatchbackend.domain.model.HelpType;
import com.tein.overcatchbackend.domain.model.User;
import com.tein.overcatchbackend.mapper.HelpMapper;
import com.tein.overcatchbackend.mapper.HelpTypeMapper;
import com.tein.overcatchbackend.service.HelpService;
import com.tein.overcatchbackend.service.HelpTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/helpType")
@RequiredArgsConstructor
public class HelpTypeResource {


    private final HelpTypeService helpTypeService;
    private final HelpTypeMapper helpTypeMapper;



    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<HelpType> getHelpTypes() {
        return helpTypeService.getHelpTypes();
    }

    @RequestMapping(value = "/getbyid", method = RequestMethod.GET)
    public HelpTypeDTO getHelpDetail(@RequestParam("helpTypeID") String helpTypeID) {
        return helpTypeService.getHelpTypeByID(Long.valueOf(helpTypeID));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public HelpTypeDTO saveHelpType(@RequestParam String helpTypeDTO) throws Exception {

        HelpTypeDTO helpTypeDTO1 = new ObjectMapper().readValue(helpTypeDTO, HelpTypeDTO.class);
        HelpType helpType = helpTypeMapper.toEntity(helpTypeDTO1);

        return helpTypeMapper.toDto(helpTypeService.saveHelpTypeTopic(helpType));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public HelpTypeDTO updateHelpType(@RequestParam String helpTypeDTO) throws Exception {

        HelpTypeDTO helpTypeDTO1 = new ObjectMapper().readValue(helpTypeDTO, HelpTypeDTO.class);
        HelpType helpType = helpTypeMapper.toEntity(helpTypeDTO1);

        return helpTypeMapper.toDto(helpTypeService.saveHelpTypeTopic(helpType));
    }
}
