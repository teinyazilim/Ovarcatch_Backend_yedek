package com.tein.overcatchbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tein.overcatchbackend.domain.model.ChatUser;
import com.tein.overcatchbackend.domain.model.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChatListDTO implements Serializable {

    private Long id;

    private List<ChatDTO> chat=new ArrayList<>();

    private ChatUser userinfo;

    private ChatUser contactInfo;

    //private List<User> users=new ArrayList<>();

//    @JsonIgnore
//    private UserDTO user;
//
//    @JsonIgnore
//    private UserDTO contact;

    public void setContact(UserDTO contact) {
        if(contact!=null){
            this.contactInfo=new ChatUser();
            this.contactInfo.setId(contact.getId());
            this.contactInfo.setEmail(contact.getEmail());
            this.contactInfo.setName(contact.getName());
            this.contactInfo.setSurname(contact.getSurname());
        }
    }

    public void setUser(UserDTO user) {
        if(user!=null){
            this.userinfo=new ChatUser();
            this.userinfo.setId(user.getId());
            this.userinfo.setEmail(user.getEmail());
            this.userinfo.setName(user.getName());
            this.userinfo.setSurname(user.getSurname());
        }
    }
}
