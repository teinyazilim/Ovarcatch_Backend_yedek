package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.ModuleTypesResponse;
import com.tein.overcatchbackend.domain.dto.ConfirmDTO;
import com.tein.overcatchbackend.domain.dto.TaskConfirmationDTO;
import com.tein.overcatchbackend.domain.model.ExpensesType;
import com.tein.overcatchbackend.domain.model.TaskConfirmation;
import com.tein.overcatchbackend.enums.TaskConfirmEnum;
import com.tein.overcatchbackend.mapper.TaskConfirmationMapper;
import com.tein.overcatchbackend.service.TaskConfirmationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/taskconfirm")
@RequiredArgsConstructor
public class TaskConfirmationResource {
    private final TaskConfirmationService taskConfirmationService;
    private final TaskConfirmationMapper taskConfirmationMapper;

    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public TaskConfirmationDTO chanceTaskConfirm(@RequestBody ConfirmDTO confirmDTO) {

        TaskConfirmEnum a=TaskConfirmEnum.INPROGRESS;
        if(confirmDTO.getTaskConfirmType().equals("INPROGRESS")){
            a=TaskConfirmEnum.INPROGRESS;
        }
        if(confirmDTO.getTaskConfirmType().equals("DONE")){
            a=TaskConfirmEnum.DONE;
        }
        if(confirmDTO.getTaskConfirmType().equals("REJECTED")){
            a=TaskConfirmEnum.REJECTED;
        }
        return  taskConfirmationMapper.toDto(taskConfirmationService.doneTaskConfirmation(a,(Long.valueOf(confirmDTO.getTaskId())),confirmDTO.getMessage()));
    }

    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/getModuleTypes", method = RequestMethod.GET)
    public List<ModuleTypesResponse> getModuleTypes() {
        List<ModuleTypesResponse> ns=taskConfirmationService.getModuleTypes();
        return  ns;
    }
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<TaskConfirmation> get(@RequestParam("Id") String Id) {
        List<TaskConfirmation> ns=taskConfirmationService.get(Long.valueOf(Id));
        return  ns;
    }
    //Git denemesi için eklendi


    @RequestMapping(value = "/gettasksbypersonelid", method = RequestMethod.GET)
    public List<TaskConfirmationDTO> getTasksByPersonelId(@RequestParam("personelId") String personelId) {
        return taskConfirmationService.getTasksByPersonelId(Long.valueOf(personelId));
    }
  
    @RequestMapping(value="/inProgress", method = RequestMethod.GET)
    public ResponseEntity<?> inProgress(){
        try{
            List<TaskConfirmation> result = taskConfirmationService.getInProgressTasksByDate();
            return ResponseEntity.ok(result);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/done", method = RequestMethod.GET)
    public ResponseEntity<?> Done() {
        try {
            List<TaskConfirmation> completed = taskConfirmationService.getDoneTasksByDate();
            return ResponseEntity.ok(completed);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
