package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.mapper.*;
import com.tein.overcatchbackend.service.*;
import com.tein.overcatchbackend.domain.dto.*;
import com.tein.overcatchbackend.domain.dto.exception.AppException;
import com.tein.overcatchbackend.domain.model.*;
import com.tein.overcatchbackend.domain.vm.ApiResponse;
import com.tein.overcatchbackend.domain.vm.JwtAuthenticationResponse;
import com.tein.overcatchbackend.domain.vm.UserProfile;
import com.tein.overcatchbackend.enums.AgreementType;
import com.tein.overcatchbackend.enums.ClientTypeEnum;
import com.tein.overcatchbackend.enums.UserType;
import com.tein.overcatchbackend.repository.*;
import com.tein.overcatchbackend.security.SecurityUtils;
import com.tein.overcatchbackend.util.GlobalVariable;
import com.tein.overcatchbackend.util.StringUtils;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserResource {

    //private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserDTOMapper userDTOMapper;
    private final UserPersonelMapper userPersonelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RolesRepository rolesRepository;
    private final MailService mailService;
    private final UserService userService;
    private final ClientService clientService;
    private final CurrentUserService currentUserService;
    private final UserCompanyMapper userCompanyMapper;
    private final PersonelRepository personelRepository;
    private final CustomerRepository customerRepository;
    private final CustomerClientRepository customerClientRepository;
    private final CustomerClientDetailMapper customerClientDetailMapper;
    private final ClientCodeService clientCodeService;


//{ "username": "000372", "password": "password5", "email":"sf" }

    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_EMPLOYEE')")
    @RequestMapping(value="/addUser", method = RequestMethod.POST)
    public ResponseEntity saveUser(@RequestBody CustomerCreateDTO customerCreateDTO) throws Exception{
        if(userRepository.findByEmail(customerCreateDTO.getEmail())!=null) {
            throw new Exception("Bu email adresi zaten kullanılıyor.");
        }

//        signUpRequest.setUserType(UserType.CUSTOMER);
        UserDTO userDTO=userDTOMapper.toEntity(customerCreateDTO);
        User user = userMapper.toEntity(userDTO);
        user.setIsPassChanged(false);
        String pass = StringUtils.generateCommonLangPassword();
        user.setPassword(passwordEncoder.encode(pass));
        mailService.sendMailHtml(user.getEmail(),pass,user.getName());
        if(userDTO.getUserType().equals(UserType.CUSTOMER)){
            user.setRoles(rolesFromStrings(Arrays.asList("CUSTOMER")));
        }else if(customerCreateDTO.getUserType().equals(UserType.EMPLOYEE)){
            user.setRoles(rolesFromStrings(Arrays.asList("EMPLOYEE","CUSTOMER")));
        }else if(customerCreateDTO.getUserType().equals(UserType.MANAGER)){
            user.setRoles(rolesFromStrings(Arrays.asList("MANAGER","EMPLOYEE","CUSTOMER")));
        }
        Customer customer = new Customer();
        user.setUserType(userDTO.getUserType());
        user.setIsActive(true);
        user.setIsDeleted(false);
        User result = userRepository.save(user);
        result.setUserFolder("user\\"+GlobalVariable.converSessiz(result.getName())+result.getId());
        User result2 = userRepository.save(result);
        customer.setUser(result2);

        Client c=new Client();
        c.setIsActive(true);
        c.setIsVatMember(customerCreateDTO.getIsVatMember());
        c.setPayment(customerCreateDTO.getPayment());
        c.setIsExisting(customerCreateDTO.getIsExisting());
        c.setAgreementType(customerCreateDTO.getAgreementType());
        c.setNotes(customerCreateDTO.getNotes());
        c.setClientTypeEnum(customerCreateDTO.getClientType());
        if(c.getClientTypeEnum() == ClientTypeEnum.SELFASSESMENT){
            c.setCode(clientCodeService.save("SA"));
        }else if(c.getClientTypeEnum() == ClientTypeEnum.LIMITED){
            if(c.getAgreementType() == AgreementType.ECAA)
                c.setCode(clientCodeService.save("A" + c.getClientTypeEnum().toString().substring(0,1)));
            else
            c.setCode(clientCodeService.save(c.getAgreementType().toString().substring(0,1) + c.getClientTypeEnum().toString().substring(0,1)));
        }else if(c.getClientTypeEnum() == ClientTypeEnum.SOLETRADE) {
            if(c.getAgreementType() == AgreementType.ECAA)
                c.setCode(clientCodeService.save(  c.getClientTypeEnum().toString().substring(0,1) + "L"));
            else
            c.setCode(clientCodeService.save(  c.getClientTypeEnum().toString().substring(0,1) + c.getAgreementType().toString().substring(0,1)));
        }
        clientService.saveUserClient(c,customer);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/auth/{email}")
                .buildAndExpand(result.getEmail()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @RequestMapping(value="/addPersonel", method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestBody UserPersonelDTO userPersonelDTO) throws Exception{
        if(userRepository.findByEmail(userPersonelDTO.getEmail())!=null) {
            throw new Exception("Bu email adresi zaten kullanılıyor.");
        }
//        signUpRequest.setUserType(UserType.CUSTOMER);

        User user= userPersonelMapper.toEntity(userPersonelDTO);
        user.setIsPassChanged(false);
        user.setIsActive(true);
        user.setIsDeleted(false);
        String pass = StringUtils.generateCommonLangPassword();
        user.setPassword(passwordEncoder.encode(pass));
        mailService.sendMailHtml(user.getEmail(),pass,user.getName());

        Customer customer = new Customer();
        Personel personel = new Personel();
        if(userPersonelDTO.getUserType().equals(UserType.CUSTOMER)){
            user.setRoles(rolesFromStrings(Arrays.asList("CUSTOMER")));
        }else if(userPersonelDTO.getUserType().equals(UserType.EMPLOYEE)){
            user.setRoles(rolesFromStrings(Arrays.asList("EMPLOYEE","CUSTOMER")));
        }else if(userPersonelDTO.getUserType().equals(UserType.MANAGER)){
            user.setRoles(rolesFromStrings(Arrays.asList("MANAGER","EMPLOYEE","CUSTOMER")));
        }
        user.setUserType(userPersonelDTO.getUserType());
        user.setIsActive(true);
        User result = userRepository.save(user);
        result.setUserFolder("user\\"+GlobalVariable.converSessiz(result.getName())+result.getId());
        User result2 = userRepository.save(result);
        customer.setUser(result2);
        personel.setUser(result2);

        if (userPersonelDTO.getDepartment() != null){
            //Employee New User Kayıt Yaptığında gerçekleşen kısım
            personel.setDepartment(userPersonelDTO.getDepartment());
        }
        if(userPersonelDTO.getUserType().equals(UserType.CUSTOMER)) {
            customerRepository.save(customer);
        }else{
            personelRepository.save(personel);
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/auth/{email}")
                .buildAndExpand(result.getEmail()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_EMPLOYEE') || hasRole('ROLE_CUSTOMER') ")
    @RequestMapping(value="/updateUser", method = RequestMethod.POST)
    public ResponseEntity updateUser(@RequestBody UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User user1 = userRepository.findById(userDTO.getId()).get();
        User userOld= userRepository.findByEmail(userDTO.getEmail());
        if(userOld!=null&&(user1!=userOld)){
            return ResponseEntity.badRequest().body(new ApiResponse(false, "This Email Already Used"));
        }else{
            User userNew= userRepository.findById(userDTO.getId()).get();
            user.setIsActive(true);
            user.setIsDeleted(false);
            user.setPassword(userNew.getPassword());

            if(user1.getUserFolder() == null){
                user.setUserFolder("user\\" + user.getName().toUpperCase(Locale.ROOT).toString() + user.getId());
            }
            else {
                user.setUserFolder(user1.getUserFolder());
            }

            if (userDTO.getUserType().equals(UserType.CUSTOMER)) {
                user.setRoles(rolesFromStrings(Arrays.asList("CUSTOMER")));
                user.setCustomer(userNew.getCustomer());
            } else if (userDTO.getUserType().equals(UserType.EMPLOYEE)) {
                user.setRoles(rolesFromStrings(Arrays.asList("EMPLOYEE")));
                user.setPersonel(userNew.getPersonel());
            } else if (userDTO.getUserType().equals(UserType.MANAGER)) {
                user.setRoles(rolesFromStrings(Arrays.asList("MANAGER")));
                user.setPersonel(userNew.getPersonel());
            }
            User user2 = userRepository.save(user);


            return ResponseEntity.ok().body(new ApiResponse(true, "User updated successfully"));
        }

//        user.getRoles().removeAll(userOld.getRoles());


    }
    @RequestMapping(value="/getcurrentuser", method = RequestMethod.GET)
    public UserDTO getAuthUser (){
       String email= SecurityUtils.getCurrentUserLogin().get();
       return userMapper.toDto(userService.getCurrrentUser(email));
    }

    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/access-token", method = RequestMethod.POST)
    public ResponseEntity<?> isAuth(@RequestBody String access_token) {
        var user=currentUserService.getCurrentUser();
        List<CustomerClient> u=new ArrayList<>();
        if(user.getCustomer()!=null){
            u=customerClientRepository.findAllByCustomerId(user.getCustomer().getId());
        }
        DataDTO dataDTO=new DataDTO().builder().email(user.getEmail())
            .name(user.getName()).surname(user.getSurname())
            .photoURL(user.getPhotoURL()).usersClient(userCompanyMapper.toDto(u)).userFolder(user.getUserFolder()).isPassChanged(user.getIsPassChanged())
            .build();
        LoginDTO loginDTO=new LoginDTO().builder().role(Arrays.asList(user.getUserType().name())).data(dataDTO)
            .build();

        JwtAuthenticationResponse a= new JwtAuthenticationResponse(SecurityUtils.getCurrentUserJWT().get(),loginDTO);
        return ResponseEntity.ok(a);
    }

    //Kullanıcının uygulamayı kullandığı dil bilgisinin kayıt altına alındığı kısım ...
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_EMPLOYEE') || hasRole('ROLE_CUSTOMER') ")
    @RequestMapping(value = "/addUserLanguage", method = RequestMethod.POST)
    public void addUserLanguage(@RequestParam String userlanguage) {

        var user = currentUserService.getCurrentUser();
        user.setUserlanguage(userlanguage);
        userService.saveUser(user);
    }

    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/changepassword", method = RequestMethod.POST)
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) throws Exception {

        var user=currentUserService.getCurrentUser();
        if(!passwordEncoder.matches(changePasswordDTO.getOldPassword(),user.getPassword())){
            throw new Exception("Eski Şifrenizi Doğru giriniz.");
        }
        mailService.sendMailHtmlChangedPassword(user.getEmail(),user.getName());
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        user.setIsPassChanged(true);


        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_EMPLOYEE')")
    @RequestMapping(value="/passiveUser", method = RequestMethod.GET)
    public ResponseEntity passiveUser(@RequestParam("id") String id){
        return userService.passiveUser(Long.parseLong(id));
    }
    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_EMPLOYEE')")
    @RequestMapping(value="/activeUser", method = RequestMethod.GET)
    public ResponseEntity activeUser(@RequestParam("id") String id){
        return userService.activeUser(Long.parseLong(id));
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_EMPLOYEE')")
    @RequestMapping(value="/deleteUser", method = RequestMethod.GET)
    public ResponseEntity deleteUser(@RequestParam("id") String id){
        return userService.deleteUserById(Long.parseLong(id));
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') || hasRole('ROLE_EMPLOYEE')")
    @RequestMapping(value="/allUsers", method = RequestMethod.GET)
    public List<UserDTO> getUser(){
        List<User> user;
        if(currentUserService.getCurrentUser().getUserType()==UserType.EMPLOYEE){
            user=userRepository.findAllByUserType(UserType.CUSTOMER.toString());
        }else{
            user=userRepository.findAllActive();
        }

        List<UserDTO> userDTO=userMapper.toDto(user);
        for(int i=0;i<user.size();i++){
            userDTO.get(i).setId(user.get(i).getId());
        }
        return userDTO;
    }

    public Set<Roles> rolesFromStrings(List<String> roles) {
        Set<Roles> rolesSet = new HashSet<>();
        roles.forEach((String item) -> {
            Roles byRoleCode = rolesRepository.findByRoleCode(item).orElseThrow(() -> new AppException("User Role not set."));
            Roles role = null;
            if (byRoleCode != null) {
                role = byRoleCode;
            } else {
                new Exception("Role not found");
            }
            rolesSet.add(role);
        });
        return rolesSet;
    }
    public Set<Roles> rolesFromStringsSet(Set<Roles> roles) {
        Set<Roles> rolesSet = new HashSet<>();
        roles.forEach((Roles item) -> {
            Roles byRoleCode = rolesRepository.findByRoleCode(item.getRoleCode()).orElseThrow(() -> new AppException("User Role not set."));
            Roles role = null;
            if (byRoleCode != null) {
                role = byRoleCode;
            } else {
                new Exception("Role not found");
            }
            rolesSet.add(role);
        });
        return rolesSet;
    }

    @GetMapping("/users/{email}")
    public UserProfile getUserProfile(@PathVariable(value = "email") String email) {
        User user = userRepository.findByEmail(email);
                //.orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        UserProfile userProfile = new UserProfile(user.getId(), user.getEmail(), user.getName(), user.getCreatedDateTime());

        return userProfile;
    }

    @GetMapping("/getUser")
    public UserDTO getUserById(@RequestParam Long id){
        return userService.findById(id);
    }
}
