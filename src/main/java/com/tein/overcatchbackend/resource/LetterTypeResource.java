package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.ClientDTO;
import com.tein.overcatchbackend.domain.dto.LetterTypeDTO;
import com.tein.overcatchbackend.domain.model.LetterType;
import com.tein.overcatchbackend.mapper.LetterTypeMapper;
import com.tein.overcatchbackend.repository.LetterRepository;
import com.tein.overcatchbackend.repository.LetterTypeRepository;
import com.tein.overcatchbackend.service.LetterTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lettertype")
@RequiredArgsConstructor
public class LetterTypeResource {


    private final LetterTypeService letterTypeService;
    private final LetterTypeMapper letterTypeMapper;
    private final LetterTypeRepository letterTypeRepository;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public LetterType saveLetterType(@RequestBody LetterTypeDTO letterTypeDTO) {
        LetterType letterType = letterTypeMapper.toEntity(letterTypeDTO);
        return letterTypeService.saveLetterType(letterType);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<LetterTypeDTO> getLetterTypes() {return letterTypeService.getAllLetterTypes();}

    @RequestMapping(value = "/getAllUsedLetterTypes", method = RequestMethod.GET)
    public List<LetterTypeDTO> getAllLetterTypes() {return letterTypeService.getAllUsed();}

    @RequestMapping(value = "/getAllForFilter", method = RequestMethod.GET)
    public List<LetterTypeDTO> getLetterTypesForFilter() {
        List<LetterType> letterTypes = letterTypeRepository.findAll();
        return letterTypeMapper.toDto(letterTypes);
    }

    @RequestMapping(value = "/getByClientType", method = RequestMethod.GET)
    public List<LetterTypeDTO> getLetterTypes(@RequestParam String clientType) {
        List<LetterTypeDTO> letterTypeDTO = letterTypeService.getLetterTypesByClientType(clientType);
        return letterTypeDTO;
    }

    //localhost:8081/api/company/detail?companyId=1740
    @RequestMapping(value = "/getbyid", method = RequestMethod.GET)
    public LetterTypeDTO getCompanyDetail(@RequestParam("letterTypeID") String letterTypeID) {
        return letterTypeService.getLetterTypeByID(Long.valueOf(letterTypeID));
    }

    @GetMapping("/delete")
    public ResponseEntity<?> deleteLetter(@RequestParam Long id){
       try {
           letterTypeService.deleteLetter(id);
       }catch (Exception e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }
        return ResponseEntity.ok("Success");
    }
}
