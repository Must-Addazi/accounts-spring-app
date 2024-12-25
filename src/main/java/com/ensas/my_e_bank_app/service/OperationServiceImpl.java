package com.ensas.my_e_bank_app.service;


import com.ensas.my_e_bank_app.dtos.AccountHistoryDTO;
import com.ensas.my_e_bank_app.dtos.AccountOperationDTO;
import com.ensas.my_e_bank_app.entities.AccountOperation;
import com.ensas.my_e_bank_app.entities.BankAccount;
import com.ensas.my_e_bank_app.enums.OperationType;
import com.ensas.my_e_bank_app.exceptions.BalanceNotSufficientException;
import com.ensas.my_e_bank_app.exceptions.BankAccountNotFoundException;
import com.ensas.my_e_bank_app.exceptions.OperationNotFoundException;
import com.ensas.my_e_bank_app.mappers.BankAccountMapper;
import com.ensas.my_e_bank_app.repositories.AccountOperationRepository;
import com.ensas.my_e_bank_app.repositories.BankAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class OperationServiceImpl implements OperationService{
    public BankAccountRepository bankAccountRepository;
    public AccountOperationRepository accountOperationRepository;
    public BankAccountMapper accountMapper;

    @Override
    public AccountOperationDTO getOperation(Long id) throws OperationNotFoundException {
        return  accountMapper.fromAccountOperation(accountOperationRepository.findById(id).orElseThrow(()->new OperationNotFoundException("Operation not found")));
    }

    @Override
    public void debit(String AccountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
     BankAccount bankAccount = bankAccountRepository.findById(AccountId).orElseThrow(
             ()->new BankAccountNotFoundException("Account not found")
     );
     if(bankAccount.getBalance()<amount)
         throw new BalanceNotSufficientException("balance not sufficient");
        AccountOperation accountOperation= AccountOperation.builder()
                .type(OperationType.DEBIT)
                .date(new Date())
                .amount(amount)
                .description(description)
                .bankAccount(bankAccount)
                .build();
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
      bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String AccountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(AccountId).orElseThrow(
                ()->new BankAccountNotFoundException("Account not found")
        );
        AccountOperation accountOperation= AccountOperation.builder()
                .type(OperationType.DEBIT)
                .date(new Date())
                .amount(amount)
                .description(description)
                .bankAccount(bankAccount)
                .build();
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String AccountIdSrc, String AccountIdDest, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
      debit(AccountIdSrc,amount,"Transfer from "+AccountIdSrc);
      credit(AccountIdDest,amount,"Transfer to "+AccountIdDest);
    }

    @Override
    public List<AccountOperationDTO> accountHistory(String accountId) {
       List<AccountOperation> accountOperations= accountOperationRepository.findByBankAccountId(accountId);
       return accountOperations.stream().map(accountOperation -> (
           accountMapper.fromAccountOperation(accountOperation)
       )).toList();
    }

}
