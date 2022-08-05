package com.tein.overcatchbackend.domain.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO  implements Serializable {
    private List<String> role;
    private DataDTO data;

}
