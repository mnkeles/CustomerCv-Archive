package com.customercvarchive.repository;

import com.customercvarchive.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer getById(Integer id);
}
