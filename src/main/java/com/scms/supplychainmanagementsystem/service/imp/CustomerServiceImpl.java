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
        log.info("[Start CustomerService - saveCustomer with username: " + customerDto.get + "]");
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new AppException("Username exists");
        }
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");
        if (userDto.getRoleId() == 1) {
            if (currentUser.getRole().getRoleID() != 1) {
                throw new AppException("You are not allow to create role ADMIN");
            }
        }
        Warehouse warehouse = new Warehouse();
        if (currentUser.getRole().getRoleID() == 1) {
            if (userDto.getRoleId() != 1) {
                warehouse.setWarehouseID(userDto.getWarehouseId());
            }
        }
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode("123@456"))
                .email(userDto.getEmail())
                .role(roleRepository.findById(userDto.getRoleId())
                        .orElseThrow(() -> new AppException("Not found role")))
                .warehouse(warehouse)
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .isActive(userDto.isActive())
                .phone(userDto.getPhone())
                .dateOfBirth(userDto.getDateOfBirth())
                .district(District.builder().districtID(userDto.getDistrictId()).build())
                .streetAddress(userDto.getStreetAddress())
                .createdDate(Instant.now())
                .createdBy(currentUser)
                .build();
        log.info("[Start save user " + user.getUsername() + " to database]");
        userRepository.saveAndFlush(user);
        log.info("[End save user " + user.getUsername() + " to database]");
        log.info("[End UserService - saveUser with username: " + userDto.getUsername() + "]");
    }
}
