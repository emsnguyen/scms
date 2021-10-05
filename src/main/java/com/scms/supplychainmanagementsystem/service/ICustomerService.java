package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.CustomerDto;
import com.scms.supplychainmanagementsystem.dto.UserDto;
import com.scms.supplychainmanagementsystem.entity.Customer;

import java.util.List;

public interface ICustomerService {

    public List<Customer> getAllCustomerInWarehouse();

    void updateCustomer(CustomerDto CustomerDto);

    void saveCustomer(CustomerDto CustomerDto);
}
