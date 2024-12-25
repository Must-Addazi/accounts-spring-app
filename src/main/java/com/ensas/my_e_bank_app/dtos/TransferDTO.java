package com.ensas.my_e_bank_app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class TransferDTO {
   private String AccountIdSrc;
   private String AccountIdDst;
   private String amount;

}
