package com.ensas.my_e_bank_app.service;


import com.ensas.my_e_bank_app.dtos.AccountHistoryDTO;
import com.ensas.my_e_bank_app.dtos.AccountOperationDTO;
import com.ensas.my_e_bank_app.exceptions.BalanceNotSufficientException;
import com.ensas.my_e_bank_app.exceptions.BankAccountNotFoundException;
import com.ensas.my_e_bank_app.exceptions.OperationNotFoundException;

import java.util.List;

public interface OperationService {
    AccountOperationDTO getOperation(Long id) throws OperationNotFoundException;
void debit(String AccountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
void credit(String AccountId, double amount, String description) throws BalanceNotSufficientException, BankAccountNotFoundException;
void transfer(String AccountIdSrc,String AccountIdDest, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
List<AccountOperationDTO> accountHistory(String accountId);


}
