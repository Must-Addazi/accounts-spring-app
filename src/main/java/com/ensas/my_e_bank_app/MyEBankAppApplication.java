package com.ensas.my_e_bank_app;

import com.ensas.my_e_bank_app.entities.AccountOperation;
import com.ensas.my_e_bank_app.entities.CurrentAccount;
import com.ensas.my_e_bank_app.entities.Customer;
import com.ensas.my_e_bank_app.entities.SavingAccount;
import com.ensas.my_e_bank_app.enums.AccountStatus;
import com.ensas.my_e_bank_app.enums.OperationType;
import com.ensas.my_e_bank_app.repositories.AccountOperationRepository;
import com.ensas.my_e_bank_app.repositories.BankAccountRepository;
import com.ensas.my_e_bank_app.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class MyEBankAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyEBankAppApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(CustomerRepository customerRepository,
										BankAccountRepository bankAccountRepository,
										AccountOperationRepository accountOperationRepository){
		return args -> {
			Stream.of("must", "said", "amine").forEach(name -> {
				Customer customer = Customer.builder().name(name).email(name + "@gmail.com").build();
				customerRepository.save(customer);
			});
			customerRepository.findAll().forEach(customer -> {
				CurrentAccount currentAccount = new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random() * 6000);
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCreatedAt(new Date());
				currentAccount.setOverDraft(870);
				currentAccount.setCustomer(customer);
				bankAccountRepository.save(currentAccount);

				SavingAccount savingAccount = new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random() * 6000);
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setCreatedAt(new Date());
				savingAccount.setInterestRate(3.8);
				savingAccount.setCustomer(customer);
				bankAccountRepository.save(savingAccount);
			});
			bankAccountRepository.findAll().forEach(bankAccount -> {
				for (int i = 0; i < 4; i++) {
					AccountOperation accountOperation = AccountOperation.builder()
							.date(new Date())
							.bankAccount(bankAccount)
							.amount(Math.random() * 100)
							.type(Math.random() > 0 ? OperationType.CREDIT : OperationType.DEBIT)
							.build();
					accountOperationRepository.save(accountOperation);
				}
			});
		};
	}
}
