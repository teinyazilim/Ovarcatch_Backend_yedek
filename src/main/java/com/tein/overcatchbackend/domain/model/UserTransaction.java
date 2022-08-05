package com.tein.overcatchbackend.domain.model;

import com.tein.overcatchbackend.domain.BaseEntity;
import com.tein.overcatchbackend.enums.ModuleTypeEnum;
import com.tein.overcatchbackend.enums.PageStatus;
import com.tein.overcatchbackend.enums.SessionStatus;
import com.tein.overcatchbackend.enums.UserType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "USER_TRANSACTION")
@EqualsAndHashCode(callSuper = true)
public class UserTransaction extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_TYPE")
    private UserType userType;

    @Enumerated(EnumType.STRING)
    @Column(name = "MODULE_TYPE")
    private ModuleTypeEnum moduleTypeEnum;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAGE_STATUS")
    private PageStatus pageStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "SESSION_STATUS")
    private SessionStatus sessionStatus;

    @Column(name = "PROCESS_DATE")
    private LocalDateTime processDate;

    @ManyToOne
    @JoinColumn(name = "USER_ID",referencedColumnName = "id")
    private User userBy;


}
