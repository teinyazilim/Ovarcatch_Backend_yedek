package com.tein.overcatchbackend.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tein.overcatchbackend.domain.BaseEntity;
import com.tein.overcatchbackend.enums.ModuleTypeEnum;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "ROLES")
@EqualsAndHashCode(callSuper = true)
public class Roles extends BaseEntity {

    @Column(name = "ROLE_CODE")
    private String roleCode;

    @Column(name = "ROLE_DESCRIPTION")
    private String roleDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "MODULE_TYPE")
    private ModuleTypeEnum moduleTypeEnum;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER)
    private List<User> users = new ArrayList<User>();

   /* public void addUser(User user) {
        this.users.add(user);
        user.getRoles().add(this);
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.getRoles().remove(this);
    }*/
}
