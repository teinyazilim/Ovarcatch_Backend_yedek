package com.tein.overcatchbackend.domain.dto;

import com.tein.overcatchbackend.enums.AgreementType;
import com.tein.overcatchbackend.enums.ClientTypeEnum;
import com.tein.overcatchbackend.enums.UserType;
import lombok.*;

import javax.persistence.Lob;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreateDTO implements Serializable {

    private Long id;

    private String password;

    private String brpNumber;

    private LocalDate brpExpireDate;

    private String name;

    private String photoURL;

    @Lob
    private byte[] photo;

    private String userFolder;

    private String payment;

    private String surname;

    private String email;

    private String msisdn;

    private Integer status;

    private UserType userType;

    private Set<RoleDTO> roles;

    private ClientTypeEnum clientType;

    private Boolean isExisting;

    private Boolean isVatMember;

    private String visaType;

    private AgreementType agreementType;

    private String notes;

//    private List<UserCompanyDTO> usersCompanyList;

//    private List<TaskCompanyDTO> tasks;
//
//    private List<TaskConfirmationDTO> taskConfirmations;

}

