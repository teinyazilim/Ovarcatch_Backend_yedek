package com.tein.overcatchbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.tein.overcatchbackend.domain.model.ChatUser;
import com.tein.overcatchbackend.domain.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class ChatDTO implements Serializable {

    private Long id;

    private ChatUser user;

//    @JsonIgnore
//    private UserDTO userInfo;

    private String message;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime time;

//   public void setUserInfo(User userInfo) {
//        //this.userInfo = userInfo;
//        if(userInfo!=null){
//            this.user=new ChatUser();
//            this.user.setId(userInfo.getId());
//            this.user.setEmail(userInfo.getEmail());
//            this.user.setName(userInfo.getName());
//            this.user.setSurname(userInfo.getSurname());
//            //this.user.setPhoto(userInfo.getPhoto());
//        }
//    }

}
