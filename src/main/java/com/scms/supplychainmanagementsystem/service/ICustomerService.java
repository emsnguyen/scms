package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.CustomerDto;
import com.scms.supplychainmanagementsystem.entity.Customer;
import com.scms.supplychainmanagementsystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerService {

    Page<Customer> getAllCustomerInWarehouse(Pageable pageable);

    Customer getCustomerByIdInWarehouse(Long customerId);

    void updateCustomer(Long customerid, CustomerDto CustomerDto);

    void saveCustomer(CustomerDto CustomerDto);

    void deleteCustomer(Long customerid);

    Page<Customer> getAllCustomer(String customername, Long warehouseId, Pageable pageble);
}
