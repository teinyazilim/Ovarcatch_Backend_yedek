package com.tein.overcatchbackend.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tein.overcatchbackend.domain.BaseEntity;
import com.tein.overcatchbackend.enums.UserType;
import com.tein.overcatchbackend.service.CurrentUserService;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "USER")
//, indexes = {
//        @Index(name = "EMAIL_X", columnList = "EMAIL", unique = true)
//}
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class User extends BaseEntity{

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHOTO_URL")
    private String photoURL;

//    @Lob
//    @Column(name = "PHOTO")
//    private byte[] photo;

    @Column(name = "USER_FOLDER")
    private String userFolder;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "ALTERNATIVE_EMAIL")
    private String alternativeEmail;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "ALTERNATIVE_MSISDN")
    private String alternativeMsisdn;

    @Column(name = "IS_PASS_CHANGED")
    private Boolean isPassChanged;

    @Column(name = "IS_DELETED")
    private Boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_TYPE")
    private UserType userType;

    @Column(name = "CONFIRMATION_CODE")
    private String confirmationCode;

    //Yeni Eklendi ...
    @Column(name = "USER_LANGUAGE")
    private String userlanguage;

    @JsonIgnore
    @OneToOne(mappedBy = "user",cascade=CascadeType.ALL, orphanRemoval = true)
    private Personel personel;

    @JsonIgnore
    @OneToOne(mappedBy = "user",cascade=CascadeType.ALL, orphanRemoval = true)
    private Customer customer;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLES", joinColumns = {
            @JoinColumn(name = "USER_ID", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID", referencedColumnName = "id")})
    private Set<Roles> roles = new HashSet<>();


    @JsonIgnore
    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Tasks> tasks = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "userBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserTransaction> userTransaction = new ArrayList<>();


  /*  @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ChatList> chatList1 = new ArrayList<>();
*/

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_CHAT_LIST", joinColumns = {
        @JoinColumn(name = "USER_ID", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "CHAT_LIST_ID", referencedColumnName = "id")})
    private Set<ChatList> chatList1 = new HashSet<>();




    /*public void setChatList(List<ChatList> chatList) {
        if (chatList != null) {
            this.chatList = new ArrayList<>();
            chatList.forEach(item -> {
                this.chatList.add(item);
                item.setUser(this);
            });
        }
    }*/

}
