package com.tein.overcatchbackend.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ChatMessage implements Serializable {

    private Long chatId;

    private Long contactId;

    private String messageText;



}
