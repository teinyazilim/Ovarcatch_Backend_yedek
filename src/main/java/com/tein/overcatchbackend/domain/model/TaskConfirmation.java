package com.tein.overcatchbackend.domain.model;

import com.tein.overcatchbackend.domain.BaseEntity;
import com.tein.overcatchbackend.enums.TaskConfirmEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "TASKS_CONFIRMATION")
@EqualsAndHashCode(callSuper = true)
public class TaskConfirmation extends BaseEntity {
    //TODO Buraya personel tablosu geldiğinde personelle ilişkilendirilecek
    @ManyToOne
    @JoinColumn(name = "PERSONEL_ID")
    private Personel personel;

    @ManyToOne
    @JoinColumn(name = "TASK_ID")
    private Tasks tasks;

    @JoinColumn(name = "MESSAGE")
    private String message;

    @Column(name = "PROCESS_DATE")
    private LocalDateTime processDate;

    @Column(name = "TASK_CREATED_DATE")
    private LocalDateTime taskCreatedTime;

    @Enumerated(EnumType.STRING)
    @Column(name= "CONFIRM_TYPE")
    private TaskConfirmEnum taskConfirm;

}
