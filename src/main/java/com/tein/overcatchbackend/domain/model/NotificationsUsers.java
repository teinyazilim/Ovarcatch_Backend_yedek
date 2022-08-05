package com.tein.overcatchbackend.domain.model;

import com.tein.overcatchbackend.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "NOTIFICATIONS_USERS")
public class NotificationsUsers extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "NOTIFICATION_ID")
    private NoticeLog noticeLog;

    @Column(name = "EMAIL")
    private String email;

    @OneToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @Column(name = "STATUS")
    private Integer status;
}
