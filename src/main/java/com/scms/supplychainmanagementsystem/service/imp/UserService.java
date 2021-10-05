package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.UserDto;
import com.scms.supplychainmanagementsystem.entity.District;
import com.scms.supplychainmanagementsystem.entity.User;
import com.scms.supplychainmanagementsystem.entity.Warehouse;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.RoleRepository;
import com.scms.supplychainmanagementsystem.repository.UserRepository;
import com.scms.supplychainmanagementsystem.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserCommon userCommon;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void updateUser(UserDto userDto) {
        log.info("[Start UserService - updateUser with username: " + userDto.getUsername() + "]");
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");
        if (userDto.getRoleId() == 1) {
            if (currentUser.getRole().getRoleID() != 1) {
                throw new AppException("You are not allow to update role ADMIN");
            }
        }
        Warehouse warehouse = new Warehouse();
        if (currentUser.getRole().getRoleID() == 1) {
            if (userDto.getRoleId() != 1) {
                warehouse.setWarehouseID(userDto.getWarehouseId());
            }
        }
        User user = User.builder()
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
                .lastModifiedBy(currentUser)
                .lastModifiedDate(Instant.now())
                .build();
        log.info("[Start save user " + user.getUsername() + " to database]");
        userRepository.saveAndFlush(user);
        log.info("[End save user " + user.getUsername() + " to database]");
        log.info("[End UserService - updateUser with username: " + userDto.getUsername() + "]");
    }

    @Override
    public void saveUser(UserDto userDto) {
        log.info("[Start UserService - saveUser with username: " + userDto.getUsername() + "]");
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


    @Override
    public User findUserById(Long userId) {
        log.info("[Start UserService - find user by userID = " + userId + "]");
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException("User not found"));
        log.info("[End UserService - find user by userID = " + userId + "]");
        return user;
    }

    @Override
    public void deleteUser(Long userId) {

    }


}
