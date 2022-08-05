package com.tein.overcatchbackend.domain.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.tein.overcatchbackend.domain.model.ChatUser;
import com.tein.overcatchbackend.domain.vm.TaskUser;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonelDTO implements Serializable {

    private String brpNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate brpExpireDate;

    private TaskUser userInfo;

    @JsonIgnore
    private UserDTO user;

    private String department;

    public void setUser(UserDTO user) {
        if(user!=null){
            this.userInfo=new TaskUser();
            this.userInfo.setId(user.getId());
            this.userInfo.setName(user.getName());
            this.userInfo.setSurname(user.getSurname());
            this.userInfo.setEmail(user.getEmail());
            this.userInfo.setMsisdn(user.getMsisdn());
            this.userInfo.setUserType(user.getUserType().toString());
        }
    }
}

