package com.tein.overcatchbackend.domain.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmDTO implements Serializable {
    private String taskId;

    private String taskConfirmType;

    private String message;

}
