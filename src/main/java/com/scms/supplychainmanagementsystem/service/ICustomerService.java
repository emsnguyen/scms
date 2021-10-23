package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.CustomerDto;
import com.scms.supplychainmanagementsystem.dto.WarehouseDto;
import com.scms.supplychainmanagementsystem.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ICustomerService {

    Page<Customer> getAllCustomerInWarehouse(Pageable pageable);

    Customer getCustomerByIdInWarehouse(Long customerId);

    void updateCustomer(Long customerid, CustomerDto CustomerDto);

    void saveCustomer(CustomerDto CustomerDto);

    void deleteCustomer(Long customerid);

    Page<Customer> getAllCustomer(String customername, Long warehouseId, Pageable pageble);

    List<WarehouseDto> getAllWarehouse();

    public void mapReapExcelDatatoDB(MultipartFile reapExcelDataFile) throws IOException;
}
