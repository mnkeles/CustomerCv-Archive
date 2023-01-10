package com.customercvarchive.service;

import com.customercvarchive.model.Customer;
import com.customercvarchive.model.dto.CustomerDto;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerDto> allCustomers();

    Customer customerById(Integer id);

    CustomerDto customerDtoById(Integer id);

    Optional customerByIdOptional(Integer id);


    CustomerDto createCustomer(CustomerDto customerDto);

    CustomerDto updateCustomer(Integer id, CustomerDto customerDto) throws Exception;

    void deleteCustomer(Integer id) throws Exception;
}
