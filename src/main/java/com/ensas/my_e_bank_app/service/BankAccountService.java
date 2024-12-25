package com.ensas.my_e_bank_app.service;



import com.ensas.my_e_bank_app.dtos.*;
import com.ensas.my_e_bank_app.entities.*;
import com.ensas.my_e_bank_app.exceptions.*;

import java.util.List;

public interface BankAccountService {
     CurrentAccountDTO saveCurrentAccount(double balance, double overDraft, Long customerId) throws CustomerNotFoundException;
     SavingAccountDTO saveSavingAccount(double balance, double interestRate, Long customerId) throws CustomerNotFoundException;
     BankAccountDTO getBankAccount(String id) throws BankAccountNotFoundException;
     BankAccountDTO updateBankAccount(String id,  BankAccount bankAccount);
     void deleteBankAccount(String id) throws BankAccountNotFoundException;
     List<BankAccountDTO> getBankAccounts();
     AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}
