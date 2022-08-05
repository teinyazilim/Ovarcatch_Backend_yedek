package com.tein.overcatchbackend.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tein.overcatchbackend.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "CHAT_LIST")
@EqualsAndHashCode(callSuper = true)
public class ChatList extends BaseEntity {

    @JsonIgnore
    @OneToMany(mappedBy = "chatList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chat> chat=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name= "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name= "CONTACT_ID")
    private User contact;

    @JsonIgnore
    @ManyToMany(mappedBy = "chatList1",fetch = FetchType.EAGER)
    private List<User> users = new ArrayList<User>();

    //@Column(name = "LAST_MESSAGE_TIME")
    //private LocalDateTime lastMessageTime;

    /*public void setChat(List<Chat> chat) {
        if (chat != null) {
            this.chat = new ArrayList<>();
            chat.forEach(item -> {
                this.chat.add(item);
                item.setChatList(this);
            });
        }
    }*/

}
