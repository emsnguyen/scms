package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.CustomerDto;
import com.scms.supplychainmanagementsystem.dto.UserDto;
import com.scms.supplychainmanagementsystem.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICustomerService {

    public Page<Customer> getAllCustomerInWarehouse(Pageable pageable);

    public Customer getCustomerByIdInWarehouse(Long customerId);

    void updateCustomer(CustomerDto CustomerDto);

    void saveCustomer(CustomerDto CustomerDto);
}
