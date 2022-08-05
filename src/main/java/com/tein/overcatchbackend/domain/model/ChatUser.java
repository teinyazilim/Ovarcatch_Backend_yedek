package com.tein.overcatchbackend.domain.model;


import lombok.Getter;
import lombok.Setter;


//@JsonIgnoreProperties(ignoreUnknown = true) // security warning fix
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ChatUser  {

      Long id;

      String name;

      String surname;

      String email;

      //@Lob
      //private byte[] photo;

}
