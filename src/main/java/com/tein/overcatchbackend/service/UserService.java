package com.tein.overcatchbackend.service;

import com.tein.overcatchbackend.domain.dto.UserDTO;
import com.tein.overcatchbackend.domain.model.User;
import com.tein.overcatchbackend.domain.vm.ApiResponse;
import com.tein.overcatchbackend.domain.vm.ResetPassword;
import com.tein.overcatchbackend.mapper.UserMapper;
import com.tein.overcatchbackend.repository.ClientRepository;
import com.tein.overcatchbackend.repository.UserRepository;
import com.tein.overcatchbackend.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User getCurrrentUser(String email){
      return  userRepository.findByEmail(email);
    }

    public ResponseEntity passiveUser(Long Id){
        try{
            userRepository.changeActive(Id);
            return ResponseEntity.ok().body(new ApiResponse(true,"User Successfully Deactived."));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new ApiResponse(false,"User Could Not Deactived."));
        }
    }

    public ResponseEntity activeUser(Long Id){
        try{
            userRepository.changeDeActive(Id);
            User user = userRepository.findById(Id).get();
            String pass = StringUtils.generateCommonLangPassword();
            user.setPassword(passwordEncoder.encode(pass));
            mailService.sendMailHtml(user.getEmail(),pass,user.getName());
            userRepository.save(user);
            return ResponseEntity.ok().body(new ApiResponse(true,"User Successfully Actived."));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new ApiResponse(false,"User Could Not Actived."));
        }
    }

    public ResponseEntity deleteUserById(Long Id){
        try{
            userRepository.deleteUser(Id);
//            List<Client> client = clientRepository.findByUserId(Id);
//            clientRepository.deleteById(client.getId());
            return ResponseEntity.ok().body(new ApiResponse(true,"User Successfully Deleted."));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new ApiResponse(false,"User Could Not Deleted."));
        }
    }

    public ResponseEntity forgotPasswordStepOne(String email){
        try{
            Boolean t=userRepository.findByEmail(email)!=null? true:false;
            int i = new Random().nextInt(900000) + 100000;

            if(t){
                User user=userRepository.findByEmail(email);
                user.setConfirmationCode(Integer.toString(i));
                userRepository.save(user);
                mailService.forgotPasswordGetCodeSend(user.getEmail(),Integer.toString(i),"localhost:3000/change-forgat-password/"+email+"/"+i);
            }else{
                return ResponseEntity.ok().body(new ApiResponse(false,"Email Not Found."));
            }
            return ResponseEntity.ok().body(new ApiResponse(true,"/change-forgat-password/"+email));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new ApiResponse(false,"Email Not Found."));
        }
    }
    public ResponseEntity resetPasswordWithMailandCode(ResetPassword resetPassword){
        try{
            Boolean isActive=userRepository.findByEmail(resetPassword.getEmail())!=null ? true:false;
            if(isActive){

                User user=userRepository.findByEmail(resetPassword.getEmail());
                if(user.getConfirmationCode()!=null){
                    if(user.getConfirmationCode().equals(resetPassword.getChangeCode())){
                        user.setPassword(passwordEncoder.encode(resetPassword.getPassword()));
                        user.setConfirmationCode(null);
                        userRepository.save(user);
                    }else{
                        return ResponseEntity.ok().body(new ApiResponse(false,"Invalid Code!"));
                    }
                }else{
                    return ResponseEntity.ok().body(new ApiResponse(false,"Code not Found. Please try again the password change request."));
                }

            }else{
                return ResponseEntity.ok().body(new ApiResponse(false,"Email is not found."));
            }

            return ResponseEntity.ok().body(new ApiResponse(true,"Password Changed. Redirecting Login ..."));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new ApiResponse(false,"Error."));
        }
    }

    public UserDTO findById(Long id){
        return userMapper.toDto(userRepository.findById(id).get());
    }


}

