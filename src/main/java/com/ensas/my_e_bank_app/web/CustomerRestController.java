package com.ensas.my_e_bank_app.web;

import com.ensas.my_e_bank_app.dtos.CustomerDTO;
import com.ensas.my_e_bank_app.entities.Customer;
import com.ensas.my_e_bank_app.exceptions.CustomerNotFoundException;
import com.ensas.my_e_bank_app.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class CustomerRestController {
    public CustomerService customerService;

    @GetMapping("/customers")
    public List<CustomerDTO> getCustomers(){
        return customerService.getCustomers();
    }
    @GetMapping("/customers/search")
    public List<CustomerDTO> searchCustomers(@RequestParam String keyword){
        return customerService.searchCustomer("%"+keyword+"%");
    }

    @GetMapping("/customer/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long id) throws CustomerNotFoundException {
        return customerService.getCustomer(id);
    }

    @PostMapping("/savecustomer")
    public CustomerDTO saveCustomer( @RequestBody CustomerDTO customerDTO){
        return customerService.saveCustomer(customerDTO);
    }

    @PutMapping("/customer/{id}")
    public CustomerDTO updateCustomer(@PathVariable(name = "id") Long customerId, @RequestParam Customer customer){
        return customerService.updateCustomer(customerId,customer);
    }

    @DeleteMapping("/deletecustomer/{id}")
    public void deleteCustomer(@PathVariable Long id) throws CustomerNotFoundException {
        customerService.deleteCustomer(id);
    }

}
