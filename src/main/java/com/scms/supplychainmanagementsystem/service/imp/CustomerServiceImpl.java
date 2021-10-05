package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.CustomerDto;
import com.scms.supplychainmanagementsystem.entity.Customer;
import com.scms.supplychainmanagementsystem.entity.District;
import com.scms.supplychainmanagementsystem.entity.User;
import com.scms.supplychainmanagementsystem.entity.Warehouse;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.CustomerRepository;
import com.scms.supplychainmanagementsystem.service.ICustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@Transactional
@Slf4j
@Service
public class CustomerServiceImpl implements ICustomerService {



    @Autowired
    private CustomerRepository customerRepository;
    private final UserCommon userCommon;


    @Override
    public List<Customer> getAllCustomerInWarehouse() {
        User currentUser = userCommon.getCurrentUser();
        return customerRepository.findByWarehouse(userCommon.getCurrentUser().getWarehouse().getWarehouseID());
    }

    @Override
    public void updateCustomer(CustomerDto customerDto) {

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
        warehouse.setWarehouseID(currentUser.getWarehouse().getWarehouseID());

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
                .district(District.builder().districtID(customerDto.getDistrictId()).build())
                .streetAddress(customerDto.getStreetAddress())
                .createdDate(Instant.now())
                .createdBy(currentUser)
                .lastModifiedBy(currentUser)
                .build();
        log.info("[Start save customer " +customer.getEmail() + " to database]");
        customerRepository.saveAndFlush(customer);
        log.info("[End save customer " + customer.getEmail() + " to database]");
        log.info("[End CustomerService - saveCuctomer with Email: " +customer.getEmail() + "]");
    }
}


