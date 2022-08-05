package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.TaskListResult;
import com.tein.overcatchbackend.domain.dto.ClientDTO;
import com.tein.overcatchbackend.domain.dto.TaskDTO;
import com.tein.overcatchbackend.domain.dto.TaskListResultDTO;
import com.tein.overcatchbackend.domain.model.ModuleType;
import com.tein.overcatchbackend.enums.ModuleTypeEnum;
import com.tein.overcatchbackend.mapper.ClientMapper;
import com.tein.overcatchbackend.mapper.TaskListResultMapper;
import com.tein.overcatchbackend.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskResource {

    private final TaskService taskService;
    private final ClientMapper clientMapper;
    private final String moduleTp= "LETTER_MODULE";
    private final TaskListResultMapper taskListResultMapper;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<TaskDTO> getCompany() {
        List<TaskDTO> ns=taskService.getTaskList();
        return  ns;
    }

    @RequestMapping(value = "/gettaskbyclientid", method = RequestMethod.GET)
    public List<TaskListResult> getTasksByClient(@RequestParam("clientId") String clientId) {
        return taskService.getTaskListByClientId(Long.valueOf(clientId));
    }

    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public List<TaskListResult> getTaskList(@RequestParam(defaultValue = "LETTER_MODULE",value = "moduleType",required = false) String moduleType ) {
        List<TaskListResult> ns=taskService.getTaskListWithConfirm(moduleType);
        return  ns;
    }

    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public List<TaskListResultDTO> getTaskListWithFilter(@RequestParam(defaultValue = "LETTER_MODULE",value = "moduleType",required = false) String moduleType,
                                                         @RequestParam String module,
                                                         @RequestParam String confirmType,
                                                         @RequestParam String search,
                                                         @RequestParam String startDate,
                                                         @RequestParam String endDate,
                                                         @RequestParam String clientTypes,
                                                         @RequestParam String aggrementTypes,
                                                         @RequestParam (required = false, name = "orderStartDate") String orderStartDate,
                                                         @RequestParam (required = false, name = "orderEndDate") String orderEndDate,
                                                         @RequestParam (required = false, name = "selectedDepartments") String selectedDepartments,
                                                         @RequestParam (required = false, name = "personelId") String personelId
                                                         ) {
        if(personelId.equals("null") || personelId.equals("undefined") || personelId.equals("")){
            personelId=null;
        }
        List<TaskListResultDTO> ns=taskService.getTaskListWithFilter(moduleType, module, confirmType, search, startDate, endDate, clientTypes, aggrementTypes, orderStartDate, orderEndDate, selectedDepartments, personelId);
        return  ns;
    }

    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/filterAdminSupport", method = RequestMethod.GET)
    public List<TaskListResultDTO> getTaskListWithFilterForAdminSupport(@RequestParam(defaultValue = "LETTER_MODULE",value = "moduleType",required = false) String moduleType,
                                                                        @RequestParam String module,
                                                                        @RequestParam String confirmType,
                                                                        @RequestParam String search,
                                                                        @RequestParam String startDate,
                                                                        @RequestParam String endDate,
                                                                        @RequestParam String clientTypes,
                                                                        @RequestParam String orderStartDate,
                                                                        @RequestParam String orderEndDate,
                                                                        @RequestParam String aggrementTypes,
                                                                        @RequestParam String selectedDepartments,
                                                                        @RequestParam (required = false, name = "personelId") String personelId
    ) {
        if(personelId.equals("null") || personelId.equals("undefined") || personelId.equals("")){
            personelId=null;
        }
        List<TaskListResultDTO> ns=taskService.getTaskListWithFilterForAdminSupport(moduleType, module, confirmType, search, startDate, endDate, clientTypes, aggrementTypes, orderStartDate, orderEndDate, selectedDepartments,personelId);
        return  ns;
    }

    //***************
    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/searchConfirm", method = RequestMethod.GET)
    public List<TaskListResult> searchTaskList(@RequestParam(defaultValue = "LETTER_MODULE",value = "moduleType",required = false) String moduleType ,
                                               @RequestParam String search) {
        List<TaskListResult> searchTaskLists = taskService.searchTaskListWithConfirm(moduleType , search);
        return  searchTaskLists;
    }

    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/getTaskModuleList", method = RequestMethod.GET)
    public List<TaskListResult> getTaskModuleList(@RequestParam(defaultValue = "LETTER_MODULE",value = "moduleType",required = false) String moduleType ,
                                                    @RequestParam String moduleTypeClick , String confirmTypeClick) {
        List<TaskListResult> searchModuleTaskLists = taskService.searchTaskModuleList(moduleType , moduleTypeClick , confirmTypeClick);
        return  searchModuleTaskLists;
    }

    // Alttakini yeni ekledim ....
    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/getTask", method = RequestMethod.GET)
    public List<ModuleType> getTaskModuleType(@RequestParam(defaultValue = "LETTER_MODULE",value = "moduleType",required = false) String moduleType) {
        List<ModuleType> searchTaskLists = taskService.getTaskModuleList(moduleType);
        return  searchTaskLists;
    }
    //***************

    // Support Admin ....
    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/getTaskAdminSupport", method = RequestMethod.GET)
    public List<ModuleType> getTaskModuleType22(@RequestParam(defaultValue = "LETTER_MODULE",value = "moduleType",required = false) String moduleType) {
        List<ModuleType> searchTaskLists = taskService.getTaskModuleListAdminSupport(moduleType);
        return  searchTaskLists;
    }
    //***************

    @RequestMapping(value = "/getclientbytaskid", method = RequestMethod.GET)
    public ClientDTO getClientByTaskId(@RequestParam("taskId") String taskId) {
        return clientMapper.toDto(taskService.getClientByTaskId(Long.valueOf(taskId)));
    }

    @RequestMapping(value = "/getModuleType", method = RequestMethod.GET)
    public ModuleTypeEnum getModuleTypeId(@RequestParam("taskId") String taskId) {
        return taskService.getTaskById(Long.valueOf(taskId));
    }
}
