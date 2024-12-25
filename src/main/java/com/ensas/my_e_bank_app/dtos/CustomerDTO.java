package com.ensas.my_e_bank_app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class CustomerDTO {

        private Long id;
        private String name;
        private String email;

}
