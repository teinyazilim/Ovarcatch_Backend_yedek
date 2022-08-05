package com.tein.overcatchbackend.domain.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataDTO  implements Serializable {

    private String name;

    private String surname;

    private String email;

    private String photoURL;

    private String displayName;

    private String userFolder;

    private Boolean isPassChanged;

    private List<CustomerClientDetailDTO> usersClient;

    public String getDisplayName() {
        return this.name+ " "+ this.surname;
    }
}
