package com.ensas.my_e_bank_app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class CreditDTO {
   private String AccountId;
   private double amount;
   private String description;
}