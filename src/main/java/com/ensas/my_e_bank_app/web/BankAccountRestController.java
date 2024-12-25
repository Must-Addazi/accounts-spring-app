package com.ensas.my_e_bank_app.web;

import com.ensas.my_e_bank_app.dtos.AccountHistoryDTO;
import com.ensas.my_e_bank_app.dtos.BankAccountDTO;
import com.ensas.my_e_bank_app.entities.BankAccount;
import com.ensas.my_e_bank_app.exceptions.BankAccountNotFoundException;
import com.ensas.my_e_bank_app.exceptions.CustomerNotFoundException;
import com.ensas.my_e_bank_app.service.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin("*")
public class BankAccountRestController {

    public BankAccountService bankAccountService;

    @GetMapping("/bankAccounts")
    public List<BankAccountDTO> getBankAcounts(){
        return bankAccountService.getBankAccounts();
    }

    @GetMapping("/banAccount/{id}")
    public BankAccountDTO getBankAccount(@PathVariable String id) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(id);
    }

    @PostMapping("/saveCurrentAccount")
    public BankAccountDTO saveCurrentAccount(@RequestParam double balance,@RequestParam double overDraft,@RequestParam Long id) throws CustomerNotFoundException {
        return bankAccountService.saveCurrentAccount(balance, overDraft, id);
    }

    @PostMapping("/saveSavingAccount")
    public BankAccountDTO saveSavingAccount(@RequestParam double balance,@RequestParam double interestRate,@RequestParam Long id) throws CustomerNotFoundException {
        return bankAccountService.saveSavingAccount(balance, interestRate, id);
    }

    @PutMapping("/updetBankAccount")
    public BankAccountDTO updetBankAccount(@RequestParam String id,@RequestParam BankAccount bankAccount){
        return bankAccountService.updateBankAccount(id,bankAccount);
    }

    @DeleteMapping("/deleteBankAccount")
    public void deleteBankAccount( @RequestParam String id) throws BankAccountNotFoundException {
        bankAccountService.deleteBankAccount(id);
    }
    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(
            @PathVariable String accountId,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "5")int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }
}
