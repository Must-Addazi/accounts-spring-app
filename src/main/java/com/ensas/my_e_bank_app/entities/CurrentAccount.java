package com.ensas.my_e_bank_app.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("CA")
@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class CurrentAccount extends BankAccount {
    private double overDraft;
}
