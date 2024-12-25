package com.ensas.my_e_bank_app.entities;

import com.ensas.my_e_bank_app.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Type")
@Data @NoArgsConstructor @AllArgsConstructor
public abstract class BankAccount {
   @Id
   private String id;
   private double balance;
   private Date createdAt;
   @Enumerated(EnumType.STRING)
   private AccountStatus status;
   @ManyToOne
   private Customer customer;
   @OneToMany(mappedBy = "bankAccount")
   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
   private List<AccountOperation> operations ;
}
