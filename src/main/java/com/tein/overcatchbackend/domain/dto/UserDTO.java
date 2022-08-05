package com.tein.overcatchbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.tein.overcatchbackend.enums.UserType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends BaseDto {
    private Long id;

    private String password;

   /* private String brpNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate brpExpireDate;*/

    private String name;

    private String photoURL;

//    @Lob
//    private byte[] photo;

    private String userFolder;

    private String payment;

    private String surname;

    private Boolean isPassChanged;

    private String email;

    private String alternativeEmail;

    private Boolean isDeleted;

    private Boolean isActive;

    private String msisdn;

    private String alternativeMsisdn;

    private Integer status;

    private UserType userType;

    private Set<RoleDTO> roles;

    //Yeni Eklendi ...
    private String userlanguage;

    private List<ChatListDTO> chatList1;

    @Transient
    private String online;

//    private List<UserCompanyDTO> usersCompanyList;

//    private List<TaskCompanyDTO> tasks;
//
//    private List<TaskConfirmationDTO> taskConfirmations;

}
