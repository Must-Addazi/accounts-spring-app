package com.ensas.my_e_bank_app.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Customer {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
private Long id;
private String name;
private String email;
@OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
private List<BankAccount> accounts;
}
