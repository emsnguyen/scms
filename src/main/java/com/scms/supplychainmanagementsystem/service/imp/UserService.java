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
import com.scms.supplychainmanagementsystem.repository.WarehouseRepository;
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
    private final WarehouseRepository warehouseRepository;

    @Override
    public void updateUser(UserDto userDto) {
        log.info("[Start UserService - updateUser with username: " + userDto.getUsername() + "]");
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");
        if (!userCommon.checkAccessUserInfoInWarehouse(userDto.getUserId())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Not allow access this warehouse's resource");
        }
        if (userDto.getRoleId() == 1) {
            if (currentUser.getRole().getRoleID() != 1) {
                throw new AppException("Not allow to update role ADMIN");
            }
        }
        User user = userRepository.findById(userDto.getUserId()).orElseThrow(() -> new AppException("User not found"));
        user.setEmail(userDto.getEmail());
        user.setRole(roleRepository.findById(userDto.getRoleId())
                .orElseThrow(() -> new AppException("Role not found")));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setActive(userDto.isActive());
        user.setPhone(userDto.getPhone());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setDistrict(District.builder().districtID(userDto.getDistrictId()).build());
        user.setStreetAddress(userDto.getStreetAddress());
        user.setLastModifiedBy(currentUser);
        user.setLastModifiedDate(Instant.now());
        if (currentUser.getRole().getRoleID() == 1) {
            user.setWarehouse(warehouseRepository.findById(userDto.getWarehouseId())
                    .orElseThrow(() -> new AppException("Warehouse not found")));
        } else {
            user.setWarehouse(currentUser.getWarehouse());
        }
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
        if (userDto.getRoleId() == null || userDto.getDistrictId() == null) {
            throw new AppException("Not fill in all required fields");
        }
//        if (currentUser.getRole().getRoleID() != 1 && !userDto.getWarehouseId().equals(currentUser.getWarehouse().getWarehouseID())) {
//            throw new AppException("Not allow to choose this warehouse");
//        }
        if (currentUser.getRole().getRoleID() != 1 && userDto.getRoleId() == 1) {
            throw new AppException("Not allow to create role ADMIN");
        }
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode("123@456"))
                .email(userDto.getEmail())
                .role(roleRepository.findById(userDto.getRoleId())
                        .orElseThrow(() -> new AppException("Role not found")))
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
        if (currentUser.getRole().getRoleID() == 1) {
            user.setWarehouse(warehouseRepository.findById(userDto.getWarehouseId())
                    .orElseThrow(() -> new AppException("Warehouse not found")));
        } else {
            user.setWarehouse(currentUser.getWarehouse());
        }
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
        if (!userCommon.checkAccessUserInfoInWarehouse(userId)) {
            throw new AppException("Not allow access this warehouse's resource");
        }
        UserDto userDto = UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .roleId(user.getRole().getRoleID())
                .warehouseId(user.getWarehouse().getWarehouseID())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .isActive(user.isActive())
                .phone(user.getPhone())
                .dateOfBirth(user.getDateOfBirth())
                .districtId(user.getDistrict().getDistrictID())
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
        if (!userCommon.checkAccessUserInfoInWarehouse(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Not allow access this warehouse's resource");
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
    public Page<User> getAllUsers(String username, Long roleId, Long warehouseId, Pageable pageable) {
        log.info("[Start UserService - Get All Users]");
        Page<User> userPage;
        User current = userCommon.getCurrentUser();
        Warehouse wh = current.getWarehouse();
        Long userId = current.getUserId();
        if (current.getRole().getRoleID() == 1) {
            userPage = userRepository.filterAllWarehouses(username, roleId, warehouseId, userId, pageable);
        } else {
            userPage = userRepository.filterInOneWarehouse(username, roleId, wh.getWarehouseID(), userId, pageable);
        }
        log.info("[End UserService - Get All Users]");
        return userPage;
    }

    @Override
    public boolean checkUserExistByUserId(Long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public void updateUserActive(Long userId, Boolean isActive) {
        log.info("[Start UserService - Update User Active " + userId + "]");
        if (!userCommon.checkAccessUserInfoInWarehouse(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Not allow update user activation");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException("User not found"));
        user.setActive(isActive);
        userRepository.saveAndFlush(user);
        log.info("[End UserService - Update User Active " + userId + "]");
    }
}
