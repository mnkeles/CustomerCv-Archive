package com.customercvarchive.controller;


import com.customercvarchive.model.dto.CustomerDto;
import com.customercvarchive.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/getAll")
    public ResponseEntity allCustomers() {
        log.info("get customer list");
        return new ResponseEntity(customerService.allCustomers(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getCustomerById(@PathVariable Integer id) {
        log.info("get customer by id:{}", id);
        return new ResponseEntity<>(customerService.customerDtoById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity createCustomer(@RequestBody CustomerDto customerDto) {
        log.info("customer create");
        return new ResponseEntity(customerService.createCustomer(customerDto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCustomer(@PathVariable Integer id,
                                         @RequestBody CustomerDto customerDto) throws Exception {
        log.info("customer update by id:{}", id);
        return new ResponseEntity(customerService.updateCustomer(id, customerDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) throws Exception {
        customerService.deleteCustomer(id);
        log.info("customer delete by id:{}", id);
        return new ResponseEntity<>( HttpStatus.OK);
    }
}
