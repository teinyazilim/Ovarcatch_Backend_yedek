package com.tein.overcatchbackend.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.tein.overcatchbackend.domain.BaseEntity;
import com.tein.overcatchbackend.enums.PriorityLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "HELP")
@EqualsAndHashCode(callSuper = true)
public class Help extends BaseEntity {


    @Enumerated(EnumType.STRING)
    @Column(name = "PRIORTIY_LEVEL")
    private PriorityLevel priorityLevel;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ANSWER")
    private String answer;

    @ManyToOne
    @JoinColumn(name = "HELP_TYPE_ID")
    private HelpType helpType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "TASK_ID")
    private Tasks task;

    @OneToOne
    @JoinColumn(name = "DOCUMENT_ID")
    private Document document;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User request_user;

    @OneToOne
    @JoinColumn(name = "RESPONDER_USER_ID")
    private User support_user;

    @Column(name = "DIVIDED")
    private String divided;

}
