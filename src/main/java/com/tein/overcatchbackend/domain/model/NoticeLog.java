package com.tein.overcatchbackend.domain.model;

import com.tein.overcatchbackend.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "NOTICE_LOG")
public class NoticeLog extends BaseEntity {
    @Column(name = "NOTI_TYPE")
    private String notiType;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "SUBJECT")
    private String subject;
    //Yeni Eklendi ...
    @OneToOne
    @JoinColumn(name = "DOCUMENT_ID")
    private Document document;


    //    @ManyToOne
    //    @JoinColumn(name = "USER_ID",referencedColumnName = "id")
    //    private User user;
    //    @Column(name = "STATUS")
    //    private Integer status;
    //    @ManyToOne
    //    @JoinColumn(name = "CLIENT_ID",referencedColumnName = "id")
    //    private Client client;
    //    @Column(name = "NOTI_TO")
    //    private String notiTo;
}
