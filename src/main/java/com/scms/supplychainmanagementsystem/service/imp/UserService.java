package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.ChangePasswordRequest;
import com.scms.supplychainmanagementsystem.dto.RoleDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

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
        if (!userCommon.checkResourcesInWarehouse(userDto.getUserId())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You are not allow access this resource");
        }
        if (userDto.getRoleId() == 1) {
            if (currentUser.getRole().getRoleID() != 1) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You are not allow to update role ADMIN");
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
        userRepository.save(user);
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
        // if (currentUser.getRole().getRoleID() == 1) {
        //   if (userDto.getRoleId() != 1) {
        warehouse.setWarehouseID(userDto.getWarehouseId());
        // }
        //}
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
    @Transactional(readOnly = true)
    public UserDto getUserById(Long userId) {
        log.info("[Start UserService - find user by userID = " + userId + "]");
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException("User not found"));
        if (!userCommon.checkResourcesInWarehouse(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You are not allow access this resource");
        }
        User current = userCommon.getCurrentUser();
        if (user.getWarehouse() != null && current != null) {
            if (user.getWarehouse().getWarehouseID() != current.getWarehouse().getWarehouseID()) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You are not allow access");
            }
        }
        UserDto userDto = UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .roleId(roleRepository.findById(user.getRole().getRoleID())
                        .orElseThrow(() -> new AppException("Not found role")).getRoleID())
                .warehouseId(user.getWarehouse() != null ? user.getWarehouse().getWarehouseID() : null)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .isActive(user.isActive())
                .phone(user.getPhone())
                .dateOfBirth(user.getDateOfBirth())
                .districtId(user.getDistrict() != null ? user.getDistrict().getDistrictID() : null)
                .streetAddress(user.getStreetAddress())
                .createdDate(Instant.now())
                .createdBy(user.getCreatedBy() != null ? user.getCreatedBy().getUsername() : null)
                .lastModifiedDate(user.getLastModifiedDate())
                .lastModifiedBy(user.getLastModifiedBy() != null ? user.getLastModifiedBy().getUsername() : null)
                .build();
        log.info("[End UserService - find user by userID = " + userId + "]");
        return userDto;
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        log.info("[Start UserService - Reset Password username " + userCommon.getCurrentUser().getUsername() + "]");

        User currentUser = userCommon.getCurrentUser();
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), currentUser.getPassword())) {
            throw new AppException("Current password is not correct");
        }
        currentUser.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(currentUser);
        log.info("[End UserService - Reset Password username = " + currentUser.getUsername() + "]");
    }

    @Override
    public void deleteUser(Long userId) {
        log.info("[Start UserService - Delete User By userID = " + userId + "]");
        if (!userCommon.checkResourcesInWarehouse(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You are not allow access this resource");
        }
        userRepository.deleteById(userId);
        log.info("[End UserService - Delete User By userID = " + userId + "]");
    }

    @Override
    public List<RoleDto> getAllRoles() {
        log.info("[Start UserService - Get All Roles]");
        List<RoleDto> roleDtoList = roleRepository.findAll()
                .stream().map(x -> new RoleDto(x.getRoleID(), x.getRoleName())).collect(Collectors.toList());
        log.info("[End UserService - Get All Roles]");
        return roleDtoList;
    }

    @Override
    public Page<User> getAllUsers(Pageable pageble) {
        Page<User> userPage;
        if (userCommon.getCurrentUser().getWarehouse() != null) {
            Long warehouseId = userCommon.getCurrentUser().getWarehouse().getWarehouseID();
            userPage = userRepository.findAllByWarehouse_WarehouseID(warehouseId, pageble);
        } else {
            userPage = userRepository.findAll(pageble);
        }
        return userPage;
    }
}
