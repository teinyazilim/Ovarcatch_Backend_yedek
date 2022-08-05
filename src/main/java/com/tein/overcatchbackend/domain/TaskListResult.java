package com.tein.overcatchbackend.domain;


import com.tein.overcatchbackend.domain.model.Tasks;
import com.tein.overcatchbackend.enums.ModuleTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class TaskListResult  {

    @Id
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "module_type")
    private ModuleTypeEnum moduleTypeEnum;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "user_folder")
    private String userFolder;

    @Column(name = "client_type")
    private String clientType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "process_date")
    private LocalDateTime processDate;

    @Column(name = "user_name")
    private String userFullName;

    @Column(name = "customer_name")
    private String customerFullName;

    @Column(name = "aggrement_type")
    private String agreementType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "confirm_date")
    private LocalDateTime confirmDate;

    @Column(name = "confirm_type")
    private String confirmType;

    @Column(name = "personel_name")
    private String personelFullName;

    @OneToOne
    @JoinColumn(name = "TASK_ID")
    private Tasks task;


    @Column(name = "department")
    private String department;
}
