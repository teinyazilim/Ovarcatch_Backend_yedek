package com.tein.overcatchbackend.service;
import com.tein.overcatchbackend.domain.model.ModuleType;
import lombok.AllArgsConstructor;
import com.tein.overcatchbackend.repository.ModuleTypeManagerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor

public class ModuleTypeManagerService {

    private final ModuleTypeManagerRepository moduleTypeManagerRepository;

    public List<ModuleType> getModuleTypes() {
        List<ModuleType> ModuleTypes = moduleTypeManagerRepository.findAllActive();
        return ModuleTypes;
    }

    public Long getModuleTypeId(Long moduleId){
        ModuleType moduleType = moduleTypeManagerRepository.getModuleTypeId(moduleId);
        Long moduleTypeId = moduleType.getModuleType().getId();
        return moduleTypeId;
    }

    public ModuleType getModuleType(Long id){
        ModuleType moduleType = moduleTypeManagerRepository.findById(id).get();
        return moduleType;
    }

    public ModuleType updateModuleType(ModuleType moduleType) {
        moduleType.setIsActive(true);
        ModuleType moduleType1 = moduleTypeManagerRepository.save(moduleType);
        return moduleType1;
    }
}
