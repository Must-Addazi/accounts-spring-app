package com.ensas.my_e_bank_app.web;


import com.ensas.my_e_bank_app.dtos.AccountOperationDTO;
import com.ensas.my_e_bank_app.exceptions.BalanceNotSufficientException;
import com.ensas.my_e_bank_app.exceptions.BankAccountNotFoundException;
import com.ensas.my_e_bank_app.exceptions.OperationNotFoundException;
import com.ensas.my_e_bank_app.service.OperationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin("*")
public class OperationRestController {
    public OperationService operationService;

    @GetMapping("operation/{id}")
    public AccountOperationDTO getOperation(@PathVariable Long id) throws OperationNotFoundException {
        return operationService.getOperation(id);
    }

    @PostMapping("/debit")
    public void debit(@RequestParam String accountId, @RequestParam double amount, @RequestParam String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        operationService.debit(accountId,amount,description);
    }

    @PostMapping("/credit")
    public void credit(@RequestParam String accountId, @RequestParam double amount, @RequestParam String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        operationService.credit(accountId,amount,description);
    }

    @PostMapping("/transfer")
    public void transfer(@RequestParam String accountIdSrc, @RequestParam String accountIdDst, @RequestParam double amount ) throws BankAccountNotFoundException, BalanceNotSufficientException {
        operationService.transfer(accountIdSrc,accountIdDst,amount);
    }
    @GetMapping("/accountHistory/{accountId}")
    public List<AccountOperationDTO> AccountHistory(@PathVariable String accountId){
        return operationService.accountHistory(accountId);
    }
}
