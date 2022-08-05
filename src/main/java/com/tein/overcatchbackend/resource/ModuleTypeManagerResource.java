package com.tein.overcatchbackend.resource;
import com.tein.overcatchbackend.domain.dto.DocumentDTO;
import com.tein.overcatchbackend.domain.dto.ExpensesTypeDTO;
import com.tein.overcatchbackend.domain.dto.ModuleTypeDTO;
import com.tein.overcatchbackend.domain.model.Document;
import com.tein.overcatchbackend.domain.model.ExpensesType;
import com.tein.overcatchbackend.domain.model.ModuleType;
import com.tein.overcatchbackend.mapper.DocumentMapper;
import com.tein.overcatchbackend.mapper.ModuleTypeMapper;
import com.tein.overcatchbackend.service.DocumentService;
import com.tein.overcatchbackend.service.ModuleTypeManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/moduletype")
@RequiredArgsConstructor
@Slf4j

public class ModuleTypeManagerResource {
    private final ModuleTypeManagerService moduleTypeManagerService;
    private final ModuleTypeMapper moduleTypeMapper;

    @RequestMapping(value = "/getModuleTypes", method = RequestMethod.GET)
    public List<ModuleType> getModuleTypes() {
        return moduleTypeManagerService.getModuleTypes();
    }

    @RequestMapping(value = "/getModuleTypeId", method = RequestMethod.GET)
    public Long getModuleTypeId(@RequestParam String id){
       Long moduleTypeId =  moduleTypeManagerService.getModuleTypeId(Long.parseLong(id));
       return moduleTypeId;
    }

    @RequestMapping(value = "/updateModuleType", method = RequestMethod.POST)
    public ModuleType updateModuleType(@RequestBody ModuleTypeDTO moduleTypeDTO) {
        ModuleType moduleType = moduleTypeManagerService.getModuleType(moduleTypeDTO.getId());
        moduleType.setResponsibleEmail(moduleTypeDTO.getResponsibleEmail());
        return moduleTypeManagerService.updateModuleType(moduleType);
    }
}
