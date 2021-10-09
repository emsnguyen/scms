package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.ChangePasswordRequest;
import com.scms.supplychainmanagementsystem.dto.RoleDto;
import com.scms.supplychainmanagementsystem.dto.UserDto;
import com.scms.supplychainmanagementsystem.entity.User;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.service.IUserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("api/manage/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final IUserService iUserService;
    private final UserCommon userCommon;

    @GetMapping("/me")
    @ApiOperation(value = "Returns the current user profile")
    public ResponseEntity<Map<String, Object>> getUserProfile() {
        log.info("[Start UserController - Get User Profile]");
        Map<String, Object> result = new HashMap<>();
        User currentUser = userCommon.getCurrentUser();
        UserDto user = UserDto.builder()
                .username(currentUser.getUsername())
                .email(currentUser.getEmail())
                .roleId(currentUser.getRole().getRoleID())
                .warehouseId(currentUser.getWarehouse() != null ? currentUser.getWarehouse().getWarehouseID() : null)
                .firstName(currentUser.getFirstName())
                .lastName(currentUser.getLastName())
                .isActive(currentUser.isActive())
                .phone(currentUser.getPhone())
                .dateOfBirth(currentUser.getDateOfBirth())
                .districtId(currentUser.getDistrict() != null ? currentUser.getDistrict().getDistrictID() : null)
                .streetAddress(currentUser.getStreetAddress())
                .createdDate(currentUser.getCreatedDate())
                .createdBy(currentUser.getUsername())
                .lastModifiedBy(currentUser.getLastModifiedBy() != null ? currentUser.getLastModifiedBy().getUsername() : null)
                .lastModifiedDate(currentUser.getLastModifiedDate())
                .build();
        result.put("data", user);
        result.put("message", OK);
        log.info("[End UserController - Get User Profile]");
        return status(HttpStatus.OK).body(result);

    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access. Disable field [userId,createdBy, createdDate,lastModifiedBy,lastModifiedDate]")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("[Start UserController -  createUser " + userDto.getUsername() + "]");
        iUserService.saveUser(userDto);
        log.info("[End UserController -  createUser " + userDto.getUsername() + "]");
        return new ResponseEntity<>("User Account Created Successfully", CREATED);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUsersInWarehouse(@RequestParam(required = false) String username,
                                                                      @RequestParam(required = false) Long roleId,
                                                                      @RequestParam(required = false) Long warehouseId,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {
        log.info("[Start UserController - Get All Users In Warehouse]");
        List<User> userList;
        Page<User> userPage;
        Pageable pageable = PageRequest.of(page, size);

        userPage = iUserService.getAllUsers(username, roleId, warehouseId, pageable);

        userList = userPage.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("data", userList);
        response.put("currentPage", userPage.getNumber());
        response.put("totalItems", userPage.getTotalElements());
        response.put("totalPages", userPage.getTotalPages());
        if (!userPage.isEmpty()) {
            response.put("message", HttpStatus.OK);
        } else {
            response.put("message", "EMPTY_RESULT");
        }
        log.info("[End UserController - Get All Users In Warehouse]");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long userId) {
        log.info("[Start UserController - Get User By User ID]");
        Map<String, Object> result = new HashMap<>();
        UserDto userDto = iUserService.getUserById(userId);
        result.put("data", userDto);
        result.put("message", OK);
        log.info("[End UserController - Get User By User ID]");
        return status(HttpStatus.OK).body(result);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access. Disable field [userId, username,createdBy, createdDate,lastModifiedBy,lastModifiedDate]")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @Valid @RequestBody UserDto userDto) {
        log.info("[Start UserController - Update User with username " + userDto.getUsername() + "]");
        if (iUserService.checkUserExistByUserId(userId)) {
            userDto.setUserId(userId);
            iUserService.updateUser(userDto);
        } else {
            throw new AppException("User not found");
        }
        log.info("[End UserController - Update User with username " + userDto.getUsername() + "]");
        return new ResponseEntity<>("User Updated Successfully", OK);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        log.info("[Start UserController - Delete User with userid = " + userId + "]");
        if (!iUserService.checkUserExistByUserId(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        iUserService.deleteUser(userId);
        log.info("[End UserController - Delete User with userid " + userId + "]");
        return new ResponseEntity<>("User Deleted Successfully", OK);
    }

    @PutMapping("/change-password")
    @ApiOperation(value = "Required login again when change pw successfully")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        log.info("[Start UserController - Change Password with username " + userCommon.getCurrentUser().getUsername() + "]");
        iUserService.changePassword(changePasswordRequest);
        log.info("[End UserController - Change Password with username " + userCommon.getCurrentUser().getUsername() + "]");
        return new ResponseEntity<>("Password Change Successfully", OK);
    }

    @GetMapping("/list-role")
    public ResponseEntity<Map<String, Object>> getAllRoles() {
        log.info("[Start UserController - Get All Roles]");
        Map<String, Object> result = new HashMap<>();
        List<RoleDto> roleDto = iUserService.getAllRoles();
        result.put("data", roleDto);
        result.put("message", OK);
        log.info("[End UserController - Get All Roles]");
        return status(HttpStatus.OK).body(result);
    }

    @PutMapping("/{userId}/{isActive}")
    public ResponseEntity<String> updateUserActive(@PathVariable Long userId, @PathVariable Boolean isActive) {
        log.info("[Start UserController - Update User Active " + userId + "]");
        if (!iUserService.checkUserExistByUserId(userId)) {
            throw new AppException("User not found");
        }
        iUserService.updateUserActive(userId, isActive);
        log.info("[End UserController - Update User Active " + userId + "]");
        return new ResponseEntity<>("Update User Active Status Successfully", OK);
    }
}
