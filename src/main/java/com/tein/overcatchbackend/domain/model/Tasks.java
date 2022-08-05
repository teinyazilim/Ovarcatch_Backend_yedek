package com.tein.overcatchbackend.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.tein.overcatchbackend.domain.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "TASKS")
@EqualsAndHashCode(callSuper = true)
public class Tasks extends BaseEntity{

    @OneToOne
    @JoinColumn(name = "MODULE_TYPE",referencedColumnName = "id")
    private ModuleType moduleType;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User userInfo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "PROCESS_DATE")
    private LocalDateTime processDate;

    @JsonIgnore
    @OneToOne(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Letter letters;

    @JsonIgnore
    @OneToMany(mappedBy = "tasks",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskConfirmation> taskConfirmations;

    public void setTaskConfirmations(List<TaskConfirmation> confirmations) {
        if (confirmations != null) {
            this.taskConfirmations = new ArrayList<>();
            confirmations.forEach(item -> {
                this.taskConfirmations.add(item);
                item.setTasks(this);
            });
        }
    }
}
