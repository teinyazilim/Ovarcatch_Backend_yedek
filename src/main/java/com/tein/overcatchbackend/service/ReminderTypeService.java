package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.dto.ReminderTypeDTO;
import com.tein.overcatchbackend.domain.model.ReminderType;
import com.tein.overcatchbackend.mapper.ReminderTypeMapper;
import com.tein.overcatchbackend.repository.ReminderTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReminderTypeService {
    private final ReminderTypeRepository reminderTypeRepository;
    private final CurrentUserService currentUserService;
    private final ReminderTypeMapper reminderTypeMapper;


    public ReminderType saveReminderType(ReminderType reminderType) {
        return reminderTypeRepository.save(reminderType);
        //reminderTypeRepository.deleteById(reminderType.getId());
    }

    public void deleteReminderType(ReminderType reminderType) {
        reminderTypeRepository.deleteById(reminderType.getId());
    }

    public List<ReminderTypeDTO> getReminderTypes() {

        return reminderTypeMapper.toDto(reminderTypeRepository.findAll());
    }

    public ReminderTypeDTO getReminderTypeByID(long reminderTypeID) {
        ReminderType reminderType = reminderTypeRepository.findById(reminderTypeID).get();
        ReminderTypeDTO reminderTypeDTO = reminderTypeMapper.toDto(reminderType);

        return reminderTypeDTO;
    }
}
