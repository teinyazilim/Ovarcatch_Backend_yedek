package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.ClientDTO;
import com.tein.overcatchbackend.domain.dto.NewCustomerClientDTO;
import com.tein.overcatchbackend.domain.model.Client;
import com.tein.overcatchbackend.domain.model.Customer;
import com.tein.overcatchbackend.mapper.ClientMapper;
import com.tein.overcatchbackend.mapper.NewCustomerClientMapper;
import com.tein.overcatchbackend.repository.CustomerRepository;
import com.tein.overcatchbackend.repository.ReminderRepository;
import com.tein.overcatchbackend.repository.UserRepository;
import com.tein.overcatchbackend.service.ClientService;
import com.tein.overcatchbackend.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientResource {

    private final ClientService clientService;
    private final ClientMapper clientMapper;
    private final NewCustomerClientMapper newCustomerClientMapper;
    private final CurrentUserService currentUserService;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final ReminderRepository reminderRepository;
    private String userId;

    @RequestMapping(value = "/getReminderTemplate", method = RequestMethod.GET)
    public List<ClientDTO> getClient(@RequestParam("reminderClientTpe") String reminderClientTpe) {
        return clientService.getClient(reminderClientTpe);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Client saveCompany(@RequestBody ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        return clientService.saveCompany(client);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Client updateCompany(@RequestBody ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        return clientService.updateCompany(client);
    }
    //localhost:8081/api/company/detail?companyId=1740
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ClientDTO getCompanyDetail(@RequestParam("clientId") String clientId) {
        return clientService.getClientDetail(Long.valueOf(clientId));
    }

    @RequestMapping(value = "/apply", method = RequestMethod.GET)
    public List<ClientDTO> getClientsApply() {
        return clientService.getClientsApply();
    }

    @RequestMapping(value = "/applyByFilter", method = RequestMethod.GET)
    public List<ClientDTO> getClientsApplyByFilter(@RequestParam String aggrementType,
                                                   @RequestParam String clientType,
                                                   @RequestParam String exist,
                                                   @RequestParam String state,
                                                   @RequestParam String search) {
        return clientService.getClientsApplyByFilter(aggrementType, clientType, exist, state, search);
    }

    @RequestMapping(value = "/applyByMultiFilter", method = RequestMethod.GET)
    public List<ClientDTO> getClientsApplyByMultiFilter(@RequestParam String aggrementType,
                                                   @RequestParam String clientType,
                                                   @RequestParam String exist,
                                                   @RequestParam String state,
                                                   @RequestParam String search) {
        return clientService.getClientsApplyByMultiFilter(aggrementType, clientType, exist, state, search);
    }


    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/companies", method = RequestMethod.GET)
    public Page<ClientDTO> getApprovedCompanies(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "0") int size) {

        Page<Client> companies = clientService.getApprovedCompanies(page, size);
        List<ClientDTO> asd = clientMapper.toDto(companies.toList());

        //Pageable paging = PageRequest.of(page, size);


        Page<ClientDTO> responseClients = new PageImpl<> (asd , companies.getPageable(),companies.getTotalElements());


        return responseClients;
    }

    @RequestMapping(value = "/addclient", method = RequestMethod.POST)
    public ClientDTO addClientForUser(@RequestBody NewCustomerClientDTO clientDTO) {
        Customer customer = userRepository.findById(clientDTO.getUserId()).get().getCustomer();
        return clientService.saveUserClient(clientMapper.toEntity(newCustomerClientMapper.toEntity(clientDTO)),customer);
    }

    //sayfalama olarak veri çekmek için geliştirildi.
    @GetMapping("/companies/all")
    public List<Client> getCompanies(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "0") int size) {
        return clientService.getCompaniesWithPage(page, size);
    }

    @RequestMapping(value = "/getClientDecline", method = RequestMethod.GET)
    public ResponseEntity getClientDecline(@RequestParam("clientId") String clientId) {
        return clientService.getClientDecline(Long.valueOf(clientId));
    }

    @RequestMapping(value = "/getCompaniesByUserId", method = RequestMethod.GET)
    public List<Client> getCompaniesByUserId(@RequestParam(required = false, name="userId") String userId) {
        return clientService.getCompaniesByUserId(Long.valueOf(userId));
    }

    @RequestMapping(value = "/getClientByFilter", method = RequestMethod.GET)
    public List<ClientDTO> getClientByFilter(@RequestParam String clientType ,
                                          @RequestParam String aggrementType ,
                                          @RequestParam String isVat) {
        return clientService.getClientByFilter(clientType , aggrementType , isVat);
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public Page<ClientDTO> getCompaniesByFilter(@RequestParam String agg,
                                      @RequestParam String clientType,
                                      @RequestParam String search,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "0") int size) {

        Page<Client> companies = clientService.getFilter(agg, clientType, search, page, size);
        List<ClientDTO> asd = clientMapper.toDto(companies.toList());

        //Pageable paging = PageRequest.of(page, size);


        Page<ClientDTO> responseClients = new PageImpl<>(asd , companies.getPageable(),companies.getTotalElements());


        return responseClients;
    }

    @RequestMapping(value = "/getClientReminderCount", method = RequestMethod.GET)
    public List<Integer> getClientReminderCount(@RequestParam String date1,
                                               @RequestParam String date2,
                                               @RequestParam String date3,
                                               @RequestParam String date4) {
        return clientService.getClientReminderCount(date1 , date2 ,date3 , date4);
    }
}
