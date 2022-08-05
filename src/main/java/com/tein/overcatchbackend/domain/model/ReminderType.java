package com.tein.overcatchbackend.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tein.overcatchbackend.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name = "REMINDER_TYPE")
@EqualsAndHashCode(callSuper = false)
public class ReminderType extends BaseEntity {

    @Column(name = "REMINDER_TYPE_NAME")
    private String reminderTypeName;

    @Column(name = "REMINDER_TEMPLATE", length = 10000)
    private String reminderTemplate;

    @Column(name = "REMINDER_CLIENT_TYPE")
    private String reminderClientType;

    @Column(name = "is_active")
    private Boolean isActive;
}
