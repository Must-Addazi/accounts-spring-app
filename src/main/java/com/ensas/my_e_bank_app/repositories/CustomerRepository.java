package com.ensas.my_e_bank_app.repositories;

import com.ensas.my_e_bank_app.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query("Select c from Customer c where c.name like :kw")
    List<Customer> searchCustomer( @Param(value = "kw") String keyword);
}
