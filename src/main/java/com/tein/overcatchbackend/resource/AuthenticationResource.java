package com.tein.overcatchbackend.resource;

import com.tein.overcatchbackend.domain.dto.ChangePasswordDTO;
import com.tein.overcatchbackend.domain.dto.DataDTO;
import com.tein.overcatchbackend.domain.dto.LoginDTO;
import com.tein.overcatchbackend.domain.model.CustomerClient;
import com.tein.overcatchbackend.domain.vm.JwtAuthenticationResponse;
import com.tein.overcatchbackend.domain.vm.LoginUser;
import com.tein.overcatchbackend.domain.vm.ResetPassword;
import com.tein.overcatchbackend.mapper.CustomerClientDetailMapper;
import com.tein.overcatchbackend.mapper.UserCompanyMapper;
import com.tein.overcatchbackend.repository.CustomerClientRepository;
import com.tein.overcatchbackend.repository.UserRepository;
import com.tein.overcatchbackend.security.jwt.TokenProvider;
import com.tein.overcatchbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationResource {

    //private final AuthenticationManager authenticationManager;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final TokenProvider tokenProvider;

    private final UserRepository userRepository;

    private final CustomerClientRepository customerClientRepository;
    private final UserCompanyMapper userCompanyMapper;
    private final CustomerClientDetailMapper customerClientDetailMapper;
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginUser loginRequest) throws Exception  {
        Authentication authentication=authenticationManagerBuilder.getObject().authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
            )
        );


       /* Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );*/

        var user=userRepository.findByEmail(loginRequest.getEmail());
        if(!user.getIsActive()){
            throw new Exception("Böyle bir kullanıcı tanımlı değildir.");
        }
        if(user==null || user.getIsDeleted()){
            throw new Exception("Böyle bir kullanıcı tanımlı değildir.");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        List<CustomerClient> u=new ArrayList<>();
        if(user.getCustomer()!=null){
            u=customerClientRepository.findAllByCustomerId(user.getCustomer().getId());
        }

        DataDTO dataDTO=new DataDTO().builder().email(user.getEmail())
                .name(user.getName()).surname(user.getSurname()).usersClient(userCompanyMapper.toDto(u)).userFolder(user.getUserFolder())
                .photoURL(user.getPhotoURL()).isPassChanged(user.getIsPassChanged())
                .build();

        LoginDTO loginDTO=new LoginDTO().builder().role(Arrays.asList(user.getUserType().name())).data(dataDTO)
                .build();

        String jwt = tokenProvider.createToken(authentication,false);
        JwtAuthenticationResponse a= new JwtAuthenticationResponse(jwt,loginDTO);
        return ResponseEntity.ok(a);
    }

      @RequestMapping(value="/forgotPasswordStepOne", method = RequestMethod.GET)
        public ResponseEntity deleteUser(@RequestParam(value = "email") String email){
            return userService.forgotPasswordStepOne(email);
        }

    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ResponseEntity<?> changePassword(@RequestBody ResetPassword resetPassword) throws Exception {
        return  userService.resetPasswordWithMailandCode(resetPassword);
    }


}
