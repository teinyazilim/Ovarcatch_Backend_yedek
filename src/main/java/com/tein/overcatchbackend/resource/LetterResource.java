package com.tein.overcatchbackend.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tein.overcatchbackend.domain.dto.ClientDTO;
import com.tein.overcatchbackend.domain.dto.LetterDTO;
import com.tein.overcatchbackend.domain.dto.LetterTypeDTO;
import com.tein.overcatchbackend.domain.model.Client;
import com.tein.overcatchbackend.domain.model.Document;
import com.tein.overcatchbackend.domain.model.Letter;
import com.tein.overcatchbackend.mapper.LetterMapper;
import com.tein.overcatchbackend.mapper.LetterTypeMapper;
import com.tein.overcatchbackend.repository.ClientRepository;
import com.tein.overcatchbackend.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@RestController
@RequestMapping("/letters")
@RequiredArgsConstructor
public class LetterResource {

    private final ModelMapper modelMapper;
    private final LetterService letterService;
    private final LetterTypeService letterTypeService;
    private final LetterMapper letterMapper;
    private final LetterTypeMapper letterTypeMapper;
    private final ClientRepository clientRepository;
    private final FileStorageService fileStorageService;
    private final MailService mailService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Letter saveLetter(@RequestParam(required = false, name = "file") MultipartFile file, @RequestParam String letter) {
        Letter finalLetter = new Letter();

        try {
            LetterDTO letterDTO = new ObjectMapper().readValue(letter, LetterDTO.class);
            finalLetter = letterMapper.toEntity(letterDTO);
            if (file != null){
                Document document = fileStorageService.loadFileforLetter(letterDTO, file);
                finalLetter.setDocument(document);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return letterService.saveLetter(finalLetter);
    }

    @RequestMapping(value = "/save/custom", method = RequestMethod.POST)
    public ResponseEntity<?> saveCustomLetter(@RequestParam(required = false, name = "file") MultipartFile file, @RequestParam String letter) {
        Letter letter1 = new Letter();
        try {
            LetterDTO letterDTO = new ObjectMapper().readValue(letter, LetterDTO.class);
            Client client = clientRepository.findById(letterDTO.getClient().getId()).get();
            letter1 = letterMapper.toEntity(letterDTO);
            letter1.setClient(client);
            //eklenen döküman
            if (file != null){
                Document document = fileStorageService.loadFileforLetter(letterDTO, file);
                letter1.setDocument(document);
            }
            letterService.saveDoneLetter(letter1);

        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Success");
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<LetterDTO> getLetters() {return letterMapper.toDto(letterService.getLetters());}

    @RequestMapping(value = "/getbyid", method = RequestMethod.GET)
    public LetterDTO getCompanyDetail(@RequestParam("letterID") String letterID) {
        return letterService.getLetterByID(Long.valueOf(letterID));
    }

    @RequestMapping(value = "/getbytaskid", method = RequestMethod.GET)
    public LetterDTO getLetterDetail(@RequestParam("taskID") String taskID) {
        return letterService.getLetterByTaskId((Long.valueOf(taskID)));
    }

    @RequestMapping(value = "/getbyclientid", method = RequestMethod.GET)
    public Page<LetterDTO> getLetterDetailByClientID(@RequestParam("clientId") String clientId,
                                                     @RequestParam String orderColumn,
                                                     @RequestParam String orderBy,
                                                     @RequestParam int page,
                                                     @RequestParam int rowsPerPage) {

        Page<Letter> letters = letterService.getLetterByClientId((Long.valueOf(clientId)), orderColumn, orderBy, page, rowsPerPage);
        List<LetterDTO> letterDTOS = letterMapper.toDto(letters.toList());
        Page<LetterDTO> result = new PageImpl<>(letterDTOS , letters.getPageable(), letters.getTotalElements());

        return result;
    }


    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_EMPLOYEE')")
    @GetMapping("get/custom")
    public ResponseEntity<List<LetterDTO>> getAllCustomLetters(){
        List<Letter> customLetters = letterService.getCustomLetters();
        return ResponseEntity.ok(letterMapper.toDto(customLetters));
    }

    @RequestMapping(value = "/lettersByMultiFilter", method = RequestMethod.GET)
    public Page<LetterDTO> findAllLettersByMultiFilter(@RequestParam String letterType,
                                                       @RequestParam String search,
                                                       @RequestParam String orderColumn,
                                                       @RequestParam String orderBy,
                                                       @RequestParam int page,
                                                       @RequestParam int rowsPerPage) {

        Page<Letter> letters = letterService.getLettersByMultiFilter(letterType, search, orderColumn, orderBy, page, rowsPerPage);
        List<LetterDTO> letterDTOS = letterMapper.toDto(letters.toList());
        Page<LetterDTO> result = new PageImpl<>(letterDTOS , letters.getPageable(), letters.getTotalElements());
        return result;
    }

    @RequestMapping(value = "/editLetter", method = RequestMethod.POST)
    public Letter editletter(@RequestBody LetterDTO letterDTO){
        return letterService.editLetter(letterDTO);
    }

}
