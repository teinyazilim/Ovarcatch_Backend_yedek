package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.dto.ClientDTO;
import com.tein.overcatchbackend.domain.model.*;
import com.tein.overcatchbackend.domain.vm.ApiResponse;
import com.tein.overcatchbackend.enums.ClientTypeEnum;
import com.tein.overcatchbackend.mapper.ClientMapper;
import com.tein.overcatchbackend.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final AddressNewRepository addressNewRepository;
    private final ClientMapper clientMapper;
    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;
    private final TaskRepository taskRepository;
    private final CustomerRepository customerRepository;
    private final CustomerClientRepository customerClientRepository;
    private final ModuleTypeRepository moduleTypeRepository;
    private final FounderOwnerRepository founderOwnerRepository;
    private final MailService mailService;

    public List<ClientDTO> getClient(String reminderClientTpe){
        List<Client> soleTraderClientLists;
        soleTraderClientLists = clientRepository.getLimitedList(reminderClientTpe , "OTHER,ECAA,TRADING");
        return clientMapper.toDto(soleTraderClientLists);
    }

    public List<ClientDTO> getLimited(String reminderClientTpe){
        List<Client> limitedClientList;
        limitedClientList = clientRepository.getClientOfReminder(reminderClientTpe , "OTHER,ECAA,TRADING");
        return clientMapper.toDto(limitedClientList);
    }

    public Client saveCompany(Client client) {
        Client client1= clientRepository.findById(client.getId()).get();
        if(client1.getClientTypeEnum()==ClientTypeEnum.LIMITED){
            client1.setCompany(client.getCompany());
        }
        else{
            client1.setFounderOwner(client.getFounderOwner());
        }
        client1.setState("1");
        client1.setAddressList(client.getAddressList());
//        client1.setSelectedInvoiceType(1);
        Client client2=clientRepository.save(client1);
        ModuleType moduleType=moduleTypeRepository.findByModuleTypeEnum1("COMPANY_CREATE");
//        ModuleType moduleType=moduleTypeRepository.findByModuleTypeEnum(ModuleTypeEnum.COMPANY_CREATE);

        Tasks tasks=new Tasks();
        tasks.setModuleType(moduleType);
        tasks.setProcessDate(LocalDateTime.now());
        tasks.setClient(client2);
        ClientDTO client3 = getClientDetail(tasks.getClient().getId());
        mailService.sendMailHtmlAddTask(client3.getFounderOwner()!=null ? client3.getFounderOwner().getEmail():client3.getCompany().getEmail(), moduleType.getResponsibleEmail(), tasks.getModuleType().getModuleTypeEnum().getName());
        tasks.setUserInfo(currentUserService.getCurrentUser());

        taskRepository.save(tasks);

        return client1;
    }

    public Client updateCompany(Client client) {
        Client client1= clientRepository.findById(client.getId()).get();
        LocalDate now = LocalDate.now();
        if(client1.getClientTypeEnum()==ClientTypeEnum.LIMITED){
            client1.getCompany().getDirectorDetails().removeAll(client1.getCompany().getDirectorDetails());
            clientRepository.save(client1);
            client1.setCompany(client.getCompany());

            client1.setNotes(client.getNotes());
            client1.setPayment(client.getPayment());
        }
        else{
            client1.setVatNumber(client.getVatNumber());
            client1.setFounderOwner(client.getFounderOwner());
            client1.setReminderDate(now);
            client1.setStatus(client.getStatus());
        }
        //Aşağıdaki Kodu Yeni Ekledim
        if(client1.getAddressNewList().size() == 0){
            // Kullanıcı Mevcut Kaydını Yeniliyor !
            client1.setNewAddressList(client.getAddressNewList() , client);
        }
        else {
            client1.setNewAddressList(client.getAddressNewList() , client);
        }
        //Yukarıdaki Kodu Yeni Ekledim

        Client client2 = clientRepository.save(client1);
        return client1;
    }
    public ClientDTO saveUserClient(Client client, Customer customer) {
        if(client.getClientTypeEnum()== ClientTypeEnum.LIMITED){
            client.setCompany(new Company());
        }
        else if(client.getClientTypeEnum() == ClientTypeEnum.SOLETRADE || client.getClientTypeEnum() == ClientTypeEnum.SELFASSESMENT){
            client.setFounderOwner(new FounderOwner());
        }
        client.setState("0");
        client.setIsActive(true);
        Client client1 = clientRepository.save(client);
        client1.setClientFolder("client\\"+client1.getClientTypeEnum()+"-"+client1.getId().toString());
        client1.setClientFileName("firstFile");
        Client client2 = clientRepository.save(client1);
        customerRepository.save(customer);
        CustomerClient customerClient =new CustomerClient();
        customerClient.setClient(client2);
        customerClient.setCustomerInfo(customer);
        customerClient.setSharePercent(100);
        customerClientRepository.save(customerClient);


        return clientMapper.toDto(client1);
    }
    public List<Client> getCompanies() {

        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);

        Page<Client> companies = clientRepository.findAll(pageable);

        return companies.toList();
    }

    public Page<Client> getApprovedCompanies(int page, int size) {




       try {
           int currentPage = page;
           int pageSize = size == 0 ? 5 : size;
           log.info("currentPage "+currentPage);
           log.info("pageSize "+pageSize);
           Pageable pageable = PageRequest.of(currentPage, pageSize);
           Page<Client> companies = clientRepository.findAllApproved(pageable);
           /*ObjectMapper mapper = new ObjectMapper();
           String jsonString = mapper
                   .writerWithDefaultPrettyPrinter()
                   .writeValueAsString(companies);
           log.info("jsonString "+jsonString);*/
           return companies;
       }catch (Exception e){

       }

       return  null;


    }

    public List<Client> getCompaniesByUserId(Long userId){
        List<Client> companies = clientRepository.findByUserId(userId);

        return companies;
    }

    public List<Integer> getClientReminderCount( String date1 ,String date2 ,String date3 ,String date4 ){
        try{
            List<Integer> counterValues = new ArrayList<Integer>();
            int firstDateCounter;
            int secondDateCounter;
            int thirdDateCounter;
            int fourthDateCounter;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");

            String firstDate = date1.replace(",","-");
            String secondDate =date2.replace(",","-");
            String thirdDate =date3.replace(",","-");
            String fourthDate =date4.replace(",","-");

            //default, ISO_LOCAL_DATE
            LocalDate firstLocalDate = LocalDate.parse(firstDate,formatter);
            LocalDate secondLocalDate = LocalDate.parse(secondDate,formatter);
            LocalDate thirdLocalDate = LocalDate.parse(thirdDate,formatter);
            LocalDate fourthLocalDate = LocalDate.parse(fourthDate,formatter);

            System.out.println("Date 1 :" +firstLocalDate);
            System.out.println("Date 2 :" +secondLocalDate);
            System.out.println("Date 3 :" +thirdLocalDate);
            System.out.println("Date 4 :" +fourthLocalDate);

            firstDateCounter = clientRepository.getClientReminderFirstCount(firstLocalDate).size();
            counterValues.add(firstDateCounter);

            secondDateCounter = clientRepository.getClientReminderSecondCount(firstLocalDate,secondLocalDate).size();
            counterValues.add(secondDateCounter);
            thirdDateCounter = clientRepository.getClientReminderThirdCount(secondLocalDate,thirdLocalDate).size();
            counterValues.add(thirdDateCounter);
            fourthDateCounter = clientRepository.getClientReminderFourthCount(thirdLocalDate,fourthLocalDate).size();
            counterValues.add(fourthDateCounter);

            System.out.println(counterValues);

            return counterValues;
            //return null;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
       return null;
    }

    public List<ClientDTO> getClientByFilter(String clientType , String aggrementType , String isVat){

        List<ClientDTO> clientList;
        if (isVat.equals("null") || isVat == null || isVat.equals("")){
           clientList = clientMapper.toDto(clientRepository.getClientByFilterWithOutVat(clientType , aggrementType));
        }
        else
        clientList = clientMapper.toDto(clientRepository.getClientByFilterWithVat(clientType , aggrementType , isVat.equals("1") ? true : false));
        return clientList;
    }

    public List<ClientDTO> getClientByNotifications(String subject , String message){
        List<Client> clientListNotifications = clientRepository.getClientOfNotifications(subject , message);
        return clientMapper.toDto(clientListNotifications);
    }

//    public List<ClientDTO> getNoticeLogFiltersOfSearch(String search ,String subject , String message){
//        List<Client> clientListNotifications = clientRepository.getNoticeLogFiltersOfSearch(search ,subject , message);
//        return clientMapper.toDto(clientListNotifications);
//    }

    public List<Client> getCompaniesWithPage(int page, int size) {
        try {
            int currentPage = page != 0 ? (page - 1) : page;
            int pageSize = size == 0 ? 20000 : size;
            Pageable pageable = PageRequest.of(currentPage, pageSize);

            Page<Client> companies = clientRepository.findAllCompany(pageable);

            return companies.toList();

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        return null;
    }

    public ClientDTO getClientDetail(long clientId) {

           Client client = clientRepository.findByClientId(clientId).get();
           ClientDTO clientDTO = clientMapper.toDto(client);
           return clientDTO;
    }

    public List<ClientDTO> getClientsApply() {
        List<Client> dto;
        if(currentUserService.getCurrentUser().getCustomer()!=null){
            dto= clientRepository.findAllApplyByUserId(currentUserService.getCurrentUser().getCustomer().getId());
        }
        else{
            dto= clientRepository.findAllApply();
        }
        List<ClientDTO> clientDTO=clientMapper.toDto(dto);
        return clientDTO;
    }

    public List<ClientDTO> getClientsApplyByFilter(String aggrementType, String clientType, String exist, String state, String search) {
        List<Client> dto;
        // "true"  => 1 or "1"
        // "false" => o or "0"
        List<String> aggTypes = new ArrayList<String>();
        List<String> clientTypes = new ArrayList<String>();
        try{
            exist = StringUtils.isEmpty(exist) ? null : (Boolean.valueOf(exist) ? "1" : "0");
            state = StringUtils.isEmpty(state) ? null : state;
        }catch (Exception e){
            System.out.println("Error Message: " + e.getMessage());
        }
        if(currentUserService.getCurrentUser().getCustomer()!=null){
            dto= clientRepository.findAllApplyByFilterByUserId(aggTypes, clientTypes, exist, state, search, currentUserService.getCurrentUser().getCustomer().getId());
        }
        else{
            dto= clientRepository.findAllApplyByFilter(aggrementType, clientType, exist, state, search);
        }
        return clientMapper.toDto(dto);
    }

    public List<ClientDTO> getClientsApplyByMultiFilter(String aggrementType, String clientType, String exist, String state, String search) {
        List<Client> dto;
        List<String> aggTypes = new ArrayList<String>();
        List<String> clientTypes = new ArrayList<String>();
        // "true"  => 1 or "1"
        // "false" => o or "0"
        try{
            exist = StringUtils.isEmpty(exist) ? null : (Boolean.valueOf(exist) ? "1" : "0");
            state = StringUtils.isEmpty(state) ? null : state;
            aggrementType = StringUtils.isEmpty(aggrementType) ? "OTHER,ECAA,TRADING" : aggrementType;
            clientType = StringUtils.isEmpty(clientType)  ? "LIMITED,SOLETRADE,SELFASSESMENT" : clientType;
            aggTypes = new ArrayList<String>(Arrays.asList(aggrementType.split(",")));
            clientTypes = new ArrayList<String>(Arrays.asList(clientType.split(",")));


        }catch (Exception e){
            System.out.println("Error Message: " + e.getMessage());
        }
        if(currentUserService.getCurrentUser().getCustomer()!=null){
            Long userID = currentUserService.getCurrentUser().getCustomer().getId();
            dto= clientRepository.findAllApplyByFilterByUserId(aggTypes, clientTypes, exist, state, search,userID);
        }
        else{
            dto= clientRepository.findAllApplyByMultiFilter(aggTypes, clientTypes, exist, state, search);
        }
        return clientMapper.toDto(dto);
    }

    public ResponseEntity getClientDecline(long clientId) {
        try{
            clientRepository.changeState(clientId,Integer.toString(2));
        }catch(Exception ex){
            return ResponseEntity.ok(new ApiResponse(false,"Error."));
        }

        return ResponseEntity.ok(new ApiResponse(true,"Successfully."));

    }


    public Page<Client> getFilter(String agg, String clientType, String search, int page, int size){

        List<String> aggTypes = new ArrayList<String>();
        List<String> clientTypes = new ArrayList<String>();

        try {
            int currentPage = page;
            int pageSize = size == 0 ? 5 : size;
            log.info("currentPage "+currentPage);
            log.info("pageSize "+pageSize);
            agg = StringUtils.isEmpty(agg) ? "OTHER,ECAA,TRADING" : agg;
            clientType = StringUtils.isEmpty(clientType)  ? "LIMITED,SOLETRADE,SELFASSESMENT" : clientType;
            aggTypes = new ArrayList<String>(Arrays.asList(agg.split(",")));
            clientTypes = new ArrayList<String>(Arrays.asList(clientType.split(",")));
            Pageable pageable = PageRequest.of(currentPage, pageSize);
            Page<Client> companies = clientRepository.findAllApprovedByFilter(aggTypes, clientTypes, search, pageable);
           /*ObjectMapper mapper = new ObjectMapper();
           String jsonString = mapper
                   .writerWithDefaultPrettyPrinter()
                   .writeValueAsString(companies);
           log.info("jsonString "+jsonString);*/
            return companies;
        }catch (Exception e){

        }

        return  null;
    }





}


