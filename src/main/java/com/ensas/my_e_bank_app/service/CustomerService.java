package com.ensas.my_e_bank_app.service;


import com.ensas.my_e_bank_app.dtos.CustomerDTO;
import com.ensas.my_e_bank_app.entities.Customer;
import com.ensas.my_e_bank_app.exceptions.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {
  CustomerDTO saveCustomer(CustomerDTO customerDTO);
  List<CustomerDTO> getCustomers();
  CustomerDTO getCustomer(Long id) throws CustomerNotFoundException;
  CustomerDTO updateCustomer(Long CustomerId, Customer customer);
  void deleteCustomer(Long id) throws CustomerNotFoundException;
  List<CustomerDTO> searchCustomer(String keyword);
}
