package com.ensas.my_e_bank_app.service;


import com.ensas.my_e_bank_app.dtos.*;

import com.ensas.my_e_bank_app.entities.*;
import com.ensas.my_e_bank_app.enums.AccountStatus;
import com.ensas.my_e_bank_app.exceptions.BankAccountNotFoundException;
import com.ensas.my_e_bank_app.exceptions.CustomerNotFoundException;
import com.ensas.my_e_bank_app.mappers.BankAccountMapper;

import com.ensas.my_e_bank_app.repositories.AccountOperationRepository;
import com.ensas.my_e_bank_app.repositories.BankAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {
    public CustomerService customerService;
    public BankAccountRepository bankAccountRepository;
    public AccountOperationRepository accountOperationRepository;
    public BankAccountMapper accountMapper;
    @Override
    public CurrentAccountDTO saveCurrentAccount(double balance, double overDraft, Long customerId) throws CustomerNotFoundException {
        CustomerDTO customerDTO= customerService.getCustomer(customerId);
        Customer customer=accountMapper.fromCustomerDTO(customerDTO);
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCustomer(customer);
        currentAccount.setCreatedAt(new Date());
        currentAccount.setStatus(AccountStatus.CREATED);
        currentAccount.setBalance(balance);
        currentAccount.setOverDraft(overDraft);
        bankAccountRepository.save(currentAccount);
        return accountMapper.fromCurrentAccount(currentAccount);
    }

    @Override
    public SavingAccountDTO saveSavingAccount(double balance, double interestRate, Long customerId) throws CustomerNotFoundException {
        CustomerDTO customerDTO= customerService.getCustomer(customerId);
        Customer customer=accountMapper.fromCustomerDTO(customerDTO);
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        savingAccount.setBalance(balance);
        savingAccount.setStatus(AccountStatus.CREATED);
        savingAccount.setCreatedAt(new Date());
        bankAccountRepository.save(savingAccount);
        return accountMapper.fromSavingAccount(savingAccount);

    }

    @Override
    public BankAccountDTO getBankAccount(String id) throws BankAccountNotFoundException {
        BankAccount bankAccount= bankAccountRepository.findById(id).orElseThrow(
                ()-> new BankAccountNotFoundException("Banck account not Found ")
        );
        if(bankAccount instanceof CurrentAccount currentAccount){
            return accountMapper.fromCurrentAccount(currentAccount);
        }else {
          SavingAccount savingAccount = (SavingAccount) bankAccount;
          return accountMapper.fromSavingAccount(savingAccount);
        }
    }

    @Override
    public BankAccountDTO updateBankAccount(String id, BankAccount updatedAccount) {
          updatedAccount.setId(id);
      BankAccount bankAccount= bankAccountRepository.save(updatedAccount);
        if(bankAccount instanceof CurrentAccount currentAccount){
            return accountMapper.fromCurrentAccount(currentAccount);
        }else {
            SavingAccount savingAccount = (SavingAccount) bankAccount;
            return accountMapper.fromSavingAccount(savingAccount);
        }
    }

    @Override
    public void deleteBankAccount(String id) throws BankAccountNotFoundException {
     BankAccountDTO bankAccountDTO= getBankAccount(id);
     BankAccount bankAccount;
        if(bankAccountDTO instanceof CurrentAccountDTO currentAccountDTO){
             bankAccount= accountMapper.fromCurrentAccountDTO(currentAccountDTO);
        }else {
            SavingAccountDTO savingAccountDTO = (SavingAccountDTO) bankAccountDTO;
             bankAccount= accountMapper.fromSavingAccountDTO(savingAccountDTO);
        }
     bankAccountRepository.delete(bankAccount);
    }

    @Override
    public List<BankAccountDTO> getBankAccounts() {
        List<BankAccount> bankAccounts= bankAccountRepository.findAll();
        return bankAccounts.stream()
                .map(bankAccount -> {
                    if (bankAccount instanceof CurrentAccount currentAccount) {
                        return accountMapper.fromCurrentAccount(currentAccount);
                    } else {
                        SavingAccount savingAccount = (SavingAccount) bankAccount;
                        return accountMapper.fromSavingAccount(savingAccount);
                    }
                })
                .toList();
    }
    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount==null) throw new BankAccountNotFoundException("Account not Found");
        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountIdOrderByDateDesc(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op -> accountMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }
}
