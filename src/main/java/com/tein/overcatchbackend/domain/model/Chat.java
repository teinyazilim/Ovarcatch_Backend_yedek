package com.tein.overcatchbackend.domain.model;

import com.tein.overcatchbackend.domain.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "CHAT")
@EqualsAndHashCode(callSuper = true)
public class Chat extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User userInfo;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "TIME")
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name= "CHAT_LIST_ID")
    private ChatList chatList;

}
