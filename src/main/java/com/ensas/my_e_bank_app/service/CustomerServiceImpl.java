package com.ensas.my_e_bank_app.service;


import com.ensas.my_e_bank_app.dtos.CustomerDTO;
import com.ensas.my_e_bank_app.entities.Customer;
import com.ensas.my_e_bank_app.exceptions.CustomerNotFoundException;
import com.ensas.my_e_bank_app.mappers.BankAccountMapper;
import com.ensas.my_e_bank_app.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService{
    public CustomerRepository customerRepository;
   public BankAccountMapper accountMapper;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer=accountMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer=customerRepository.save(customer);
        return accountMapper.fromCustomer(savedCustomer);
    }

    public List<CustomerDTO> getCustomers(){
        List<Customer> customers= customerRepository.findAll();
        return customers.stream()
                .map(customer -> accountMapper.fromCustomer(customer))
                .toList();
    }
    public CustomerDTO getCustomer(Long id) throws CustomerNotFoundException {
        Customer customer= customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer Not found"));
        return accountMapper.fromCustomer(customer);
    }

    @Override
    public CustomerDTO updateCustomer(Long customerId,Customer customer) {
        customer.setId(customerId);
        Customer updatedcustomer= customerRepository.save(customer);
        return accountMapper.fromCustomer(updatedcustomer);
    }

    @Override
    public void deleteCustomer(Long id) throws CustomerNotFoundException {
        CustomerDTO customerDTO = getCustomer(id);
        Customer customer=accountMapper.fromCustomerDTO(customerDTO);
     customerRepository.delete(customer);
    }

    @Override
    public List<CustomerDTO> searchCustomer(String keyword) {
        List<Customer> customerList = customerRepository.searchCustomer(keyword);
      return customerList.stream().map(customer -> accountMapper.fromCustomer(customer)).toList();
    }
}
