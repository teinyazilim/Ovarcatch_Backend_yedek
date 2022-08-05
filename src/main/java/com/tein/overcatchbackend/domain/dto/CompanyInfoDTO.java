package com.tein.overcatchbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tein.overcatchbackend.enums.PageStatus;
import com.tein.overcatchbackend.enums.SessionStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyInfoDTO {
    private Long id;
    private String companyName;

    private String fullName;

    private LocalDate birthDay;

    private String placeOfBirth;

    private String phone;

    private String ninoNumber;

    private Boolean isAnkara;

    private String companyTitle;

    private String utr;

    private String email;

    private PageStatus pageStatus;

    private SessionStatus sessionStatus;

    private String note;

    private Set<DirectorDetailDTO> directorDetails;

    private List<AddressDTO> addressList;

//    private List<UserCompanyDTO> usersCompanies;

    private FounderOwnerDTO soleTradeCompany;

    private List<DocumentDTO> documents;

}
