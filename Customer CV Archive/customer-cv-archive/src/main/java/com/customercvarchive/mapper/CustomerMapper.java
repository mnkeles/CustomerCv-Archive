package com.customercvarchive.mapper;

import com.customercvarchive.model.Customer;
import com.customercvarchive.model.dto.CustomerDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toCustomer(CustomerDto customerDto){
        return Customer.builder().customerId(customerDto.getCustomerId())
                .firstName(customerDto.getFirstName()).lastName(customerDto.getLastName())
                .phoneNumber(customerDto.getPhoneNumber()).email(customerDto.getEmail())
                .build();
    }

    public CustomerDto toCustomerDto(Customer customer){
        return CustomerDto.builder().customerId(customer.getCustomerId())
                .firstName(customer.getFirstName()).lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .build();
    }
}
