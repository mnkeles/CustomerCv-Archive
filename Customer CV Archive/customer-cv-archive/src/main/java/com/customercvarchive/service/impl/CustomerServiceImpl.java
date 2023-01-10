package com.customercvarchive.service.impl;

import com.customercvarchive.exception.EntityNotFoundException;
import com.customercvarchive.mapper.CustomerMapper;
import com.customercvarchive.model.Customer;
import com.customercvarchive.model.dto.CustomerDto;
import com.customercvarchive.repository.CustomerRepository;
import com.customercvarchive.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDto> allCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerDto> customerDtoList = new ArrayList<>();

        for (Customer customer : customerList) {
            customerDtoList.add(customerMapper.toCustomerDto(customer));
        }
        return customerDtoList;
    }

    @Override
    public Optional<Customer> customerByIdOptional(Integer id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer customerById(Integer id) {
        return customerRepository.getById(id);

    }


    @Override
    public CustomerDto customerDtoById(Integer id) {
        return customerByIdOptional(id).map(customerMapper::toCustomerDto).orElseThrow();
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = customerMapper.toCustomer(customerDto);
        customerRepository.save(customer);
        return customerMapper.toCustomerDto(customer);
    }

    @Override
    public CustomerDto updateCustomer(Integer id, CustomerDto customerDto) throws Exception {
        Optional<Customer> optionalCustomer=customerByIdOptional(id);
        if (optionalCustomer == null || optionalCustomer.equals("")) {
            throw new EntityNotFoundException("Customer",id);
        } else {
            optionalCustomer.ifPresent(customer -> {
                customer = customerMapper.toCustomer(customerDto);
                customerRepository.save(customer);
            });
        }
        return optionalCustomer.map(customerMapper::toCustomerDto).orElseThrow();
    }

    @Override
    public void deleteCustomer(Integer id) throws Exception {
        if (customerByIdOptional(id) == null || customerByIdOptional(id).equals("")) {
            throw new EntityNotFoundException("Customer",id);
        } else {
            customerRepository.deleteById(id);
        }

    }

}
