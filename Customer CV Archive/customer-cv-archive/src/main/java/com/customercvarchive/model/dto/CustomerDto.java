package com.customercvarchive.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    private Integer customerId;
    private  String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
