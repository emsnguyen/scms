package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.CustomerDto;
import com.scms.supplychainmanagementsystem.dto.RoleDto;
import com.scms.supplychainmanagementsystem.dto.WarehouseDto;
import com.scms.supplychainmanagementsystem.entity.Customer;
import com.scms.supplychainmanagementsystem.entity.District;
import com.scms.supplychainmanagementsystem.entity.User;
import com.scms.supplychainmanagementsystem.entity.Warehouse;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.CustomerRepository;
import com.scms.supplychainmanagementsystem.repository.WarehouseRepository;
import com.scms.supplychainmanagementsystem.service.ICustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.basic.BasicIconFactory;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Transactional
@Slf4j
@Service
public class CustomerServiceImpl implements ICustomerService {


    @Autowired
    private CustomerRepository customerRepository;
    private final UserCommon userCommon;
    private final WarehouseRepository warehouseRepository;


    @Override
    public Page<Customer> getAllCustomerInWarehouse(Pageable pageable) {
        User currentUser = userCommon.getCurrentUser();
        return customerRepository.findByWarehouse(userCommon.getCurrentUser().getWarehouse().getWarehouseID(), pageable);
    }


    @Override
    public Customer getCustomerByIdInWarehouse(Long customerId) {
        User currentUser = userCommon.getCurrentUser();
        Customer customer = new Customer();
        if(currentUser.getRole().getRoleID()!=1){
         customer = customerRepository.findByCustomerIdAnhInWarehouse(customerId, userCommon.getCurrentUser().getWarehouse().getWarehouseID());}
        else{ customer = customerRepository.findByCustomerId(customerId);
        }
        return customer;
    }


    @Override
    public void updateCustomer(Long customerid, CustomerDto customerDto) {
        log.info("[Start CustomerService - UpdateCustomer with email: " + customerDto.getEmail() + "]");

        log.info("[Start get current user]");

        User currentUser = userCommon.getCurrentUser();


        log.info("[End get current user : " + currentUser.getUsername() + "]");


        Warehouse warehouse = new Warehouse();
        if(currentUser.getRole().getRoleID()!=1){
            warehouse.setWarehouseID(currentUser.getWarehouse().getWarehouseID());
            if(currentUser.getWarehouse().getWarehouseID()!=customerRepository.findByCustomerId(customerid).getWarehouse().getWarehouseID()){
                throw new AppException("you cant update in another Warehouse");
        }
        }else{
            warehouse.setWarehouseID(customerDto.getWarehouseId());
        }

        Customer customer = Customer.builder()
                .customerId(customerid)
                .customerCode(customerDto.getCustomerCode())
                .CustomerType(customerDto.getCustomerType())
                .customerName(customerDto.getCustomerName())
                .email(customerDto.getEmail())
                .warehouse(warehouse)
                .phone(customerDto.getPhone())
                .DateOfBirth(customerDto.getDateOfBirth())
                .Gender(customerDto.getGender())
                .Facebook(customerDto.getFacebook())
                .CompanyName(customerDto.getCompanyName())
                .Note(customerDto.getNote())
                .TaxCode(customerDto.getTaxCode())
                .district(District.builder().districtID(customerDto.getDistrictId()).build())
                .streetAddress(customerDto.getStreetAddress())
                .createdDate(Instant.now())
                .createdBy(currentUser)
                .lastModifiedBy(currentUser)
                .build();
        log.info("[Start update customer " + customer.getEmail() + " to database]");
        customerRepository.save(customer);
        log.info("[End update customer " + customer.getEmail() + " to database]");
        log.info("[End CustomerService - updateCustomer with Email: " + customer.getEmail() + "]");
    }

    @Override
    public void saveCustomer(CustomerDto customerDto) {
        log.info("[Start CustomerService - saveCustomer with email: " + customerDto.getEmail() + "]");
        if (customerRepository.existsByEmail(customerDto.getEmail())) {
            throw new AppException("Email exists");
        }
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");

        Warehouse warehouse = new Warehouse();
        if(currentUser.getRole().getRoleID()!=1){
            warehouse.setWarehouseID(currentUser.getWarehouse().getWarehouseID());
        }else{
            warehouse.setWarehouseID(customerDto.getWarehouseId());
        }

        Customer customer = Customer.builder()
                .customerCode(customerDto.getCustomerCode())
                .CustomerType(customerDto.getCustomerType())
                .customerName(customerDto.getCustomerName())
                .email(customerDto.getEmail())
                .warehouse(warehouse)
                .phone(customerDto.getPhone())
                .DateOfBirth(customerDto.getDateOfBirth())
                .Gender(customerDto.getGender())
                .Facebook(customerDto.getFacebook())
                .CompanyName(customerDto.getCompanyName())
                .Note(customerDto.getNote())
                .TaxCode(customerDto.getTaxCode())
                .district(District.builder().districtID(customerDto.getDistrictId()).build())
                .streetAddress(customerDto.getStreetAddress())
                .createdDate(Instant.now())
                .createdBy(currentUser)
                .lastModifiedBy(currentUser)
                .build();
        log.info("[Start save customer " + customer.getEmail() + " to database]");
        customerRepository.saveAndFlush(customer);
        log.info("[End save customer " + customer.getEmail() + " to database]");
        log.info("[End CustomerService - saveCustomer with Email: " + customer.getEmail() + "]");
    }

    @Override
    public void deleteCustomer(Long customerid) {
        User currentUser = userCommon.getCurrentUser();
        if(currentUser.getRole().getRoleID()!=1){
        customerRepository.deleteCustomer(customerid, userCommon.getCurrentUser().getWarehouse().getWarehouseID());}
        else{
            customerRepository.deleteCustomerAdmin(customerid);
        }
    }

    @Override
    public Page<Customer> getAllCustomer(String customername, Long warehouseId, Pageable pageable) {
        log.info("[Start CustomerService - Get All Customer]");
        Page<Customer> customerPage;
        User current = userCommon.getCurrentUser();
        Warehouse wh = current.getWarehouse();
        Long userId = current.getUserId();
        if (current.getRole().getRoleID() != 1) {
            customerPage = customerRepository.filterInOneWarehouse(customername, wh.getWarehouseID(), pageable);

        } else {
            customerPage = customerRepository.filterAllWarehouses(customername, warehouseId, pageable);
        }
        log.info("[End CustomerService - Get All Customer]");
        return customerPage;
    }

    @Override
    public List<WarehouseDto> getAllWarehouse() {
        log.info("[Start CustomerService - Get All Warehouse]");
        List<WarehouseDto> warehouseList = warehouseRepository.findAll()
                .stream().map(x -> new WarehouseDto(x.getWarehouseID(), x.getWarehouseName(), x.getAddress())).collect(Collectors.toList());
        log.info("[End UserService - Get All Roles]");
        return warehouseList;
    }
}


