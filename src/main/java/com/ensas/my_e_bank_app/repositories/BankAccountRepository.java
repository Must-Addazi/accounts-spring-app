package com.ensas.my_e_bank_app.repositories;


import com.ensas.my_e_bank_app.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
