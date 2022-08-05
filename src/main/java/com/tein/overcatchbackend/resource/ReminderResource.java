package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.ClientDTO;
import com.tein.overcatchbackend.domain.dto.ReminderDTO;
import com.tein.overcatchbackend.domain.dto.ReminderTypeDTO;
import com.tein.overcatchbackend.domain.model.Reminder;
import com.tein.overcatchbackend.domain.model.ReminderType;
import com.tein.overcatchbackend.mapper.ReminderMapper;
import com.tein.overcatchbackend.repository.ReminderRepository;
import com.tein.overcatchbackend.repository.ReminderTypeRepository;
import com.tein.overcatchbackend.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reminder")
@RequiredArgsConstructor
public class ReminderResource {

    private final ReminderService reminderService;
    private final ReminderMapper reminderMapper;
    private final ReminderRepository reminderRepository;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void saveReminderType(@RequestBody ReminderDTO reminderDTO) {
        Reminder reminder = reminderMapper.toEntity(reminderDTO);
        try{
            reminderRepository.save(reminder);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ReminderDTO getReminderTypes() {
        return reminderService.getReminder();
    }


    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public List<ClientDTO> getReminderCompanyList() {
        return reminderService.getReminderCompanyList();
    }
}
