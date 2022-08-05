package com.tein.overcatchbackend.domain.model;

import com.tein.overcatchbackend.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "REMINDER")
@EqualsAndHashCode(callSuper = false)
public class Reminder extends BaseEntity {

    @Column(name = "REMINDER_FIRST_DATE")
    private LocalDate reminderFirstDate;

    @Column(name = "REMINDER_SECOND_DATE")
    private LocalDate reminderSecondDate;

    @Column(name = "REMINDER_THIRD_DATE")
    private LocalDate reminderThirdDate;

    @Column(name = "REMINDER_FOURTH_DATE")
    private LocalDate reminderFourthDate;

    @Column(name = "REMINDER_RESET_DATE")
    private LocalDate reminderResetDate;


    //@JoinColumn(name = "REMINDER_TYPE_ID")
    @OneToOne
    private ReminderType reminderType;
}
