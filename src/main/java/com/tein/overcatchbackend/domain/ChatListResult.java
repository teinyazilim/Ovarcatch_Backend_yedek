package com.tein.overcatchbackend.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Getter
@Setter
@Entity
public class ChatListResult {

    @Id
    @Column(name = "chat_list_id")
    private Long chat_list_id;

    @Column(name = "users")
    private String users;



}
