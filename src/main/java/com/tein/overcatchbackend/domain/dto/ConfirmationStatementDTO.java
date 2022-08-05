package com.tein.overcatchbackend.domain.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmationStatementDTO extends BaseDto {

   private String next_made_up_to;
   private String next_due;
   private String overdue;
   private String last_made_up_to;



}
