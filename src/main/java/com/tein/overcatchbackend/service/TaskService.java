package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.TaskListResult;
import com.tein.overcatchbackend.domain.dto.ClientDTO;
import com.tein.overcatchbackend.domain.dto.TaskDTO;
import com.tein.overcatchbackend.domain.dto.TaskListResultDTO;
import com.tein.overcatchbackend.domain.model.*;
import com.tein.overcatchbackend.enums.AgreementType;
import com.tein.overcatchbackend.enums.ClientTypeEnum;
import com.tein.overcatchbackend.enums.ModuleTypeEnum;
import com.tein.overcatchbackend.enums.TaskConfirmEnum;
import com.tein.overcatchbackend.mapper.TaskListResultMapper;
import com.tein.overcatchbackend.mapper.TaskMapper;
import com.tein.overcatchbackend.repository.ModuleTypeRepository;
import com.tein.overcatchbackend.repository.TaskListRepository;
import com.tein.overcatchbackend.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.*;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TaskListResultMapper taskListResultMapper;
    private final TaskListRepository taskListRepository;
    private final CurrentUserService currentUserService;
    private final ClientService clientService;
    private final MailService mailService;
    private final ModuleTypeRepository moduleTypeRepository;
//    private final TaskConfirmationService taskConfirmationService;

    public List<TaskListResult> getTaskListWithConfirm(String moduleType) {
        List<TaskListResult> tt=taskListRepository.findAllTaskListWithConfirmation(moduleType);
        return tt;
    }
//    public void findDifference(LocalDate start_date,
//                       LocalDate end_date)
//        {
//
//            // find the period between
//            // the start and end date
//            Period diff
//                    = Period
//                    .between(start_date,
//                            end_date);
//
//            // Print the date difference
//            // in years, months, and days
//            System.out.print(
//                    "Difference "
//                            + "between two dates is: ");
//
//            // Print the result
//            System.out.printf(
//                    "%d years, %d months"
//                            + " and %d days ",
//                    diff.getYears(),
//                    diff.getMonths(),
//                    diff.getDays());
//        }
//
//        // Driver Code
//        public static void main(String[] args)
//        {
//            // Start date
//            LocalDate start_date
//                    = LocalDate.of(2018, 01, 10);
//
//            // End date
//            LocalDate end_date
//                    = LocalDate.of(2020, 06, 10);
//
//            // Function Call
//            findDifference(start_date,
//                    end_date);
//        }

    public List<TaskListResultDTO> getTaskListWithFilter(String moduleType, String module, String confirmType, String search, String startDate, String endDate, String clientTypes, String aggrementTypes, String orderStartDate, String orderEndDate, String selectedDepartments, String personelId) {
        if (module.equals("null"))
            module = null;
        if (confirmType.equals("null"))
            confirmType = null;

        Long persId = null;
        if(personelId != null){
            persId = Long.parseLong(personelId);
        }
        List<String> aggrementTypeList = new ArrayList<String>();
        List<String> clientTypeList = new ArrayList<String>();
        List<String> confirmTypes = new ArrayList<String>();
        List<String> moduleList = new ArrayList<String>();
        List<String> departments = new ArrayList<String>();

        if(StringUtils.isEmpty(module)){
            List<ModuleType> moduleTypes = getTaskModuleList(moduleType);
            for (ModuleType moduleType1: moduleTypes) {
                moduleList.add(moduleType1.getModuleTypeEnum().toString());
            }
        }else{
            moduleList = new ArrayList<String>(Arrays.asList(module.split(",")));
        }
        if(StringUtils.isEmpty(confirmType)){
            confirmTypes.add(TaskConfirmEnum.INPROGRESS.toString());
            confirmTypes.add("PENDING");
        }else{
            confirmTypes = new ArrayList<String>(Arrays.asList(confirmType.split(",")));
        }

        if (StringUtils.isEmpty(clientTypes)){
            ClientTypeEnum[] clientTypeEnums = ClientTypeEnum.values();
            for (ClientTypeEnum clientTypeEnum: clientTypeEnums) {
                clientTypeList.add(clientTypeEnum.toString());
            }
        }else{
            clientTypeList = new ArrayList<String>(Arrays.asList(clientTypes.split(",")));
        }
        if (StringUtils.isEmpty(aggrementTypes)){
            AgreementType[] agreementTypes = AgreementType.values();
            for (AgreementType agreementType: agreementTypes) {
                aggrementTypeList.add(agreementType.toString());
            }
        }else{
            aggrementTypeList = new ArrayList<String>(Arrays.asList(aggrementTypes.split(",")));
        }
        if (StringUtils.isEmpty(selectedDepartments)){
            departments.add("Admin");
            departments.add("Accounts");
            departments.add("VAT");
            departments.add("Payroll");
        }else{
            departments = new ArrayList<String>(Arrays.asList(selectedDepartments.split(",")));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate start = !StringUtils.isEmpty(startDate) ? LocalDate.parse(startDate) : null;
        LocalDate end = !StringUtils.isEmpty(endDate) ? LocalDate.parse(endDate) : null;
        LocalDate orderStartDate1 = !StringUtils.isEmpty(orderStartDate)? LocalDate.parse(orderStartDate): null;
        LocalDate orderEndDate1 = !StringUtils.isEmpty(orderEndDate)? LocalDate.parse(orderEndDate): null;

        List<TaskListResult> tt=taskListRepository.findByFilter(moduleType, moduleList, confirmTypes, search, start, end, clientTypeList, aggrementTypeList, orderStartDate1, orderEndDate1, departments, persId);
        List<TaskListResultDTO> taskListResultDTO = taskListResultMapper.toDto(tt);
        List<TaskListResultDTO> taskListResultDTOS = new ArrayList<TaskListResultDTO>();

        for(TaskListResultDTO taskListResultDTO1: taskListResultDTO) {
            LocalDateTime confirmDate = taskListResultDTO1.getConfirmDate();
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime orderDate = taskListResultDTO1.getProcessDate();

            if (confirmDate == null) {
                taskListResultDTO1.setProcessTime(" Not Approved! ");
            } else {
                LocalDate now1= now.toLocalDate();
                LocalDate confirmDate1= confirmDate.toLocalDate();
                LocalDate orderDate1= orderDate.toLocalDate();

                if (taskListResultDTO1.getConfirmType().equals("INPROGRESS") || taskListResultDTO1.getConfirmType().equals("PENDING")) {
                    String fark = "";
                    Integer _day = 0;
                    Integer _hour = now.getHour() - confirmDate.getHour();
                    Integer _minute = now.getMinute() - confirmDate.getMinute();

                    Period diff = Period.between(now1,confirmDate1);
                    Integer _month = diff.getMonths();
                    Integer _year = diff.getYears();
                    if(diff.getDays() < 0){
                        _day = -1 * (diff.getDays());
                    }else{
                        _day = diff.getDays();
                    }
                    if(_minute<0){
                        _hour = _hour -1;
                        _minute = _minute + 60;
                    }
                    if(_hour<0){
                        _day = _day-1;
                        _hour = _hour + 24;
                    }
                    if(_year>0){
                        fark =  String.valueOf(_year) + " years \n"
                                + String.valueOf(_month) + " mounts \n"
                                + String.valueOf(_day) + " days \n"
                                + String.valueOf(_hour) + " hours \n"
                                + String.valueOf(_minute) + " minutes ";
                        taskListResultDTO1.setProcessTime(fark);
                    }
                    if(_month>0){
                        fark =  String.valueOf(_month) + " mounts \n"
                                + String.valueOf(_day) + " days \n"
                                + String.valueOf(_hour) + " hours \n"
                                + String.valueOf(_minute) + " minutes ";
                        taskListResultDTO1.setProcessTime(fark);
                    }
                    fark = String.valueOf(_day) + " days \n"
                            + String.valueOf(_hour) + " hours \n"
                            + String.valueOf(_minute) + " minutes ";
                    taskListResultDTO1.setProcessTime(fark);
                }else{
                    String fark = "";
                    Integer _day = 0;
                    Integer _hour = confirmDate.getHour() - orderDate.getHour();
                    Integer _minute = confirmDate.getMinute()-orderDate.getMinute();

                    Period diff = Period.between(confirmDate1,orderDate1);
                    Integer _year = diff.getYears();
                    Integer _month = diff.getMonths();
                    if(diff.getDays() < 0){
                        _day = (-1) * (diff.getDays());

                    }else{
                        _day = diff.getDays();
                    }
                    if(_minute<0){
                        _hour = _hour -1;
                        _minute = _minute + 60;
                    }
                    if(_hour<0){
                        _day = _day-1;
                        _hour = _hour + 24;
                    }
                    if(_month<0){
                        _month = -1 * _month;
                    }
//                    fark = String.valueOf(_day) + " days \n"
//                            + String.valueOf(_hour) + " hours \n"
//                            + String.valueOf(_minute) + " minutes ";
//                    taskListResultDTO1.setProcessTime(fark);
                    fark =  String.valueOf(_year) + " years \n"
                            + String.valueOf(_month) + " mounts \n"
                            + String.valueOf(_day) + " days \n"
                            + String.valueOf(_hour) + " hours \n"
                            + String.valueOf(_minute) + " minutes ";
                    taskListResultDTO1.setProcessTime(fark);
                }
            }
            taskListResultDTOS.add(taskListResultDTO1);
        }
        return taskListResultDTOS;
    }


    public List<TaskListResultDTO> getTaskListWithFilterForAdminSupport(String moduleType, String module, String confirmType, String search, String startDate, String endDate, String clientTypes, String aggrementTypes,String orderStartDate, String orderEndDate, String selectedDepartments, String personelId) {
        if (module.equals("null"))
            module = null;
        if (confirmType.equals("null"))
            confirmType = null;

        Long persId = null;
        if(personelId != null){
            persId = Long.parseLong(personelId);
        }
        List<String> moduleTypeList = new ArrayList<String>();
        List<String> aggrementTypeList = new ArrayList<String>();
        List<String> clientTypeList = new ArrayList<String>();
        List<String> confirmTypes = new ArrayList<String>();
        List<String> moduleList = new ArrayList<String>();
        List<String> departments = new ArrayList<String>();

        if(StringUtils.isEmpty(moduleType)){
            List<ModuleType> moduleTypes = getTaskModuleList(moduleType);
            for (ModuleType moduleType1: moduleTypes) {
                moduleTypeList.add(moduleType1.getModuleTypeEnum().toString());
            }
        }else{
            moduleTypeList = new ArrayList<String>(Arrays.asList(moduleType.split(",")));
        }
        if(StringUtils.isEmpty(module)){
            List<ModuleType> moduleTypes = getTaskModuleListAdminSupport(moduleType);
            for (ModuleType moduleType1: moduleTypes) {
                moduleList.add(moduleType1.getModuleTypeEnum().toString());
            }
        }else{
            moduleList = new ArrayList<String>(Arrays.asList(module.split(",")));
        }
        if(StringUtils.isEmpty(confirmType)){
            confirmTypes.add(TaskConfirmEnum.INPROGRESS.toString());
            confirmTypes.add("PENDING");
        }else{
            confirmTypes = new ArrayList<String>(Arrays.asList(confirmType.split(",")));
        }

        if (StringUtils.isEmpty(clientTypes)){
            ClientTypeEnum[] clientTypeEnums = ClientTypeEnum.values();
            for (ClientTypeEnum clientTypeEnum: clientTypeEnums) {
                clientTypeList.add(clientTypeEnum.toString());
            }
        }else{
            clientTypeList = new ArrayList<String>(Arrays.asList(clientTypes.split(",")));
        }
        if (StringUtils.isEmpty(aggrementTypes)){
            AgreementType[] agreementTypes = AgreementType.values();
            for (AgreementType agreementType: agreementTypes) {
                aggrementTypeList.add(agreementType.toString());
            }
        }else{
            aggrementTypeList = new ArrayList<String>(Arrays.asList(aggrementTypes.split(",")));
        }
        if (StringUtils.isEmpty(selectedDepartments)){
            departments.add("Admin");
            departments.add("Accounts");
            departments.add("VAT");
            departments.add("Payroll");
        }else{
            departments = new ArrayList<String>(Arrays.asList(selectedDepartments.split(",")));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate start = !StringUtils.isEmpty(startDate) ? LocalDate.parse(startDate) : null;
        LocalDate end = !StringUtils.isEmpty(endDate) ? LocalDate.parse(endDate) : null;
        LocalDate oStartDate = !StringUtils.isEmpty(orderStartDate)? LocalDate.parse(orderStartDate) : null;
        LocalDate oEndDate = !StringUtils.isEmpty(orderEndDate)? LocalDate.parse(orderEndDate) : null;

        List<TaskListResult> tt=taskListRepository.findByFilterAdminSupport(moduleTypeList, moduleList, confirmTypes, search, start, end, clientTypeList, aggrementTypeList, oStartDate, oEndDate, departments, persId);
        List<TaskListResultDTO> dtos = taskListResultMapper.toDto(tt);
        List<TaskListResultDTO> taskListResultDTOS = new ArrayList<TaskListResultDTO>();
//        String confirm_type = "";
        for(TaskListResultDTO taskListResultDTO1: dtos){
            LocalDateTime confirmDate = taskListResultDTO1.getConfirmDate();
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime orderDate = taskListResultDTO1.getProcessDate();

            if (confirmDate == null) {
                taskListResultDTO1.setProcessTime(" Not Approved! ");
            } else {
                LocalDate now1= now.toLocalDate();
                LocalDate confirmDate1= confirmDate.toLocalDate();
                LocalDate orderDate1= orderDate.toLocalDate();

                if (taskListResultDTO1.getConfirmType().equals("INPROGRESS") || taskListResultDTO1.getConfirmType().equals("PENDING")) {
                    String fark = "";

                    Integer _hour = now.getHour() - confirmDate.getHour();
                    Integer _minute = now.getMinute() - confirmDate.getMinute();
                    Integer _day = 0;

                    Period diff = Period.between(now1,confirmDate1);
                    Integer _year = diff.getYears();
                    Integer _month = diff.getMonths();

                    if(diff.getDays() < 0){
                        _day = -1 * (diff.getDays());
                    }else{
                        _day = diff.getDays();
                    }
                    if(_minute<0){
                        _hour = _hour -1;
                        _minute = _minute + 60;
                    }
                    if(_hour<0){
                        _day = _day-1;
                        _hour = _hour + 24;
                    }
//                    fark = String.valueOf(_day) + " days \n"
//                            + String.valueOf(_hour) + " hours \n"
//                            + String.valueOf(_minute) + " minutes ";
                    fark =  String.valueOf(_year) + " years \n"
                            + String.valueOf(_month) + " mounts \n"
                            + String.valueOf(_day) + " days \n"
                            + String.valueOf(_hour) + " hours \n"
                            + String.valueOf(_minute) + " minutes ";
                    taskListResultDTO1.setProcessTime(fark);
                    taskListResultDTO1.setProcessTime(fark);
                }else{
                    String fark = "";
                    Integer _day = 0;
                    Integer _hour = confirmDate.getHour() - orderDate.getHour();
                    Integer _minute = confirmDate.getMinute()-orderDate.getMinute();

                    Period diff = Period.between(confirmDate1,orderDate1);
                    Integer _year = diff.getYears();
                    Integer _month = diff.getMonths();

                    if(diff.getDays() < 0){
                        _day = -1 * (diff.getDays());
                    }else{
                        _day = diff.getDays();
                    }
                    if(_minute<0){
                        _hour = _hour -1;
                        _minute = _minute + 60;
                    }
                    if(_hour<0){
                        _day = _day-1;
                        _hour = _hour + 24;
                    }
//                    fark = String.valueOf(_day) + " days \n"
//                            + String.valueOf(_hour) + " hours \n"
//                            + String.valueOf(_minute) + " minutes ";
//                    taskListResultDTO1.setProcessTime(fark);
                    fark =  String.valueOf(_year) + " years \n"
                            + String.valueOf(_month) + " mounts \n"
                            + String.valueOf(_day) + " days \n"
                            + String.valueOf(_hour) + " hours \n"
                            + String.valueOf(_minute) + " minutes ";
                    taskListResultDTO1.setProcessTime(fark);
                }
            }
            taskListResultDTOS.add(taskListResultDTO1);
        }
        return taskListResultDTOS;
    }

    //***************
    public List<TaskListResult> searchTaskListWithConfirm(String moduleType , String search) {
        List<TaskListResult> searchTaskList = taskListRepository.searchTaskList(moduleType,search);
        return searchTaskList;
    }

    public List<TaskListResult> searchTaskModuleList(String moduleType , String moduleTypeClick , String confirmTypeClick) {
        List<TaskListResult> searchModuleTaskList = taskListRepository.searchModuleTaskList(moduleType , moduleTypeClick , confirmTypeClick);
        return searchModuleTaskList;
    }

    public List<ModuleType> getTaskModuleList(String moduleType ) {
        List<ModuleType> taskModuleTypeList = moduleTypeRepository.getTaskModuleTypeList(moduleType);
        return taskModuleTypeList;
    }

    public List<ModuleType> getTaskModuleListAdminSupport(String moduleType ) {
        List<String> moduleList = new ArrayList<String>();
        if(StringUtils.isEmpty(moduleType)){
            List<ModuleType> moduleTypes = getTaskModuleList(moduleType);
            for (ModuleType moduleType1: moduleTypes) {
                moduleList.add(moduleType1.getModuleTypeEnum().toString());
            }
        }else{
            moduleList = new ArrayList<String>(Arrays.asList(moduleType.split(",")));
        }
        List<ModuleType> taskModuleTypeList = moduleTypeRepository.getTaskModuleTypeListAdminSupport(moduleList);
        return taskModuleTypeList;
    }

    //***************
    public List<TaskDTO> getTaskList() {

        return taskMapper.toDto(taskRepository.findAll());
    }

    public List<TaskListResult> getTaskListByClientId(long clientId) {
        List<TaskListResult> tasks = taskListRepository.findAllByClientId(clientId);
//        List<TaskDTO> taskDTO = taskMapper.toDto(tasks);
        return tasks;
    }

    public Tasks addTask(Client client, ModuleType moduleType){

        Tasks tasks=new Tasks();
        tasks.setModuleType(moduleType);
        tasks.setProcessDate(LocalDateTime.now());
        tasks.setClient(client);
        ClientDTO client1=clientService.getClientDetail(tasks.getClient().getId());

        //Eğer Kullanıcnın user_language kısmı null ise default olarak Ingilizce olan template gönderiyorum ...
        if(client1.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage() == null){
            client1.getCustomerClients().get(0).getCustomerInfo().getUserInfo().setUserlanguage("en");
        }

        else if(client1.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage().equals("tr")){
            mailService.sendMailHtmlAddTaskTR(client1.getFounderOwner()!=null ? client1.getFounderOwner().getEmail():client1.getCompany().getEmail(), moduleType.getResponsibleEmail(), tasks.getModuleType().getModuleTypeEnum().getName());
        }
        else if(client1.getCustomerClients().get(0).getCustomerInfo().getUserInfo().getUserlanguage().equals("en")){
            mailService.sendMailHtmlAddTask(client1.getFounderOwner()!=null ? client1.getFounderOwner().getEmail():client1.getCompany().getEmail(), moduleType.getResponsibleEmail(), tasks.getModuleType().getModuleTypeEnum().getName());
        }

        tasks.setUserInfo(currentUserService.getCurrentUser());

        return taskRepository.save(tasks);
    }

    public Client getClientByTaskId(long taskId){
        Tasks c=taskRepository.findById(taskId).get();
        return c.getClient();
    }
    public ModuleTypeEnum getTaskById(long taskId){
        Tasks c=taskRepository.findById(taskId).get();
        return c.getModuleType().getModuleTypeEnum();

    }
}
