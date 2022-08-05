package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.ReminderTypeDTO;
import com.tein.overcatchbackend.domain.model.ReminderType;
import com.tein.overcatchbackend.domain.vm.ApiResponse;
import com.tein.overcatchbackend.mapper.ReminderTypeMapper;
import com.tein.overcatchbackend.service.ReminderTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/remindertype")
@RequiredArgsConstructor
public class ReminderTypeResource {
    private final ReminderTypeService reminderTypeService;
    private final ReminderTypeMapper reminderTypeMapper;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ReminderType saveReminderType(@RequestBody ReminderTypeDTO reminderTypeDTO) {
        ReminderType reminderType = reminderTypeMapper.toEntity(reminderTypeDTO);
        return reminderTypeService.saveReminderType(reminderType);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity deleteReminderType(@RequestBody ReminderTypeDTO reminderTypeDTO) {
        ReminderType reminderType = reminderTypeMapper.toEntity(reminderTypeDTO);
        try{
            reminderTypeService.deleteReminderType(reminderType);
            return ResponseEntity.ok().body(new ApiResponse(true,"Reminder Template deleted ."));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new ApiResponse(false,"Reminder Template not deleted"));
        }
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<ReminderTypeDTO> getReminderTypes() {return reminderTypeService.getReminderTypes();}

    @RequestMapping(value = "/getbyid", method = RequestMethod.GET)
    public ReminderTypeDTO getCompanyDetail(@RequestParam("reminderTypeID") String reminderTypeID) {
        return reminderTypeService.getReminderTypeByID(Long.valueOf(reminderTypeID));
    }
}
