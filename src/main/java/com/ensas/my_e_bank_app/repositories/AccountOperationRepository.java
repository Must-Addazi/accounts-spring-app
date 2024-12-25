package com.ensas.my_e_bank_app.repositories;

import com.ensas.my_e_bank_app.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
    List<AccountOperation> findByBankAccountId(String accountId);

    Page<AccountOperation> findByBankAccountIdOrderByDateDesc(String accountId, Pageable pageable);
}