package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.ChangePasswordRequest;
import com.scms.supplychainmanagementsystem.dto.RoleDto;
import com.scms.supplychainmanagementsystem.dto.UserDto;
import com.scms.supplychainmanagementsystem.entity.User;
import com.scms.supplychainmanagementsystem.entity.Warehouse;
import com.scms.supplychainmanagementsystem.service.IUserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<UserDto> getUserProfile() {
        log.info("[Start UserController - Get User Profile]");
        User currentUser = userCommon.getCurrentUser();
        Warehouse warehouse = new Warehouse();
        if (currentUser.getRole().getRoleID() != 1) {
            warehouse = currentUser.getWarehouse();
        }
        UserDto user = UserDto.builder()
                .username(currentUser.getUsername())
                .email(currentUser.getEmail())
                .roleId(currentUser.getRole().getRoleID())
                .warehouseId(warehouse.getWarehouseID())
                .firstName(currentUser.getFirstName())
                .lastName(currentUser.getLastName())
                .isActive(currentUser.isActive())
                .phone(currentUser.getPhone())
                .dateOfBirth(currentUser.getDateOfBirth())
                .districtId(currentUser.getDistrict().getDistrictID())
                .streetAddress(currentUser.getStreetAddress())
                .createdDate(currentUser.getCreatedDate())
                .createdBy(currentUser.getUsername())
                .lastModifiedBy(currentUser.getLastModifiedBy().getUsername())
                .lastModifiedDate(currentUser.getLastModifiedDate())
                .build();
        log.info("[End UserController - Get User Profile]");
        return status(HttpStatus.OK).body(user);

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
    public ResponseEntity<List<UserDto>> getAllUsersInWarehouse() {
        log.info("[Start UserController - Get All Users In Warehouse]");
        // TODO:
        List<UserDto> users = new ArrayList<>();
        log.info("[End UserController - Get All Users In Warehouse]");
        return status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        log.info("[Start UserController - Get User By User ID]");
        try {
            UserDto userDto = iUserService.getUserById(userId);
            log.info("[End UserController - Get User By User ID]");
            return status(HttpStatus.OK).body(userDto);
        } catch (NullPointerException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SOME_DATA_NOT_EXIST");
        }
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access. Disable field [userId, username,createdBy, createdDate,lastModifiedBy,lastModifiedDate]")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @Valid @RequestBody UserDto userDto) {
        log.info("[Start UserController - Update User with username " + userDto.getUsername() + "]");
        iUserService.getUserById(userId);
        iUserService.updateUser(userDto);
        log.info("[End UserController - Update User with username " + userDto.getUsername() + "]");
        return new ResponseEntity<>("User Updated Successfully", OK);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        log.info("[Start UserController - Delete User with userid = " + userId + "]");
        try {
            iUserService.deleteUser(userId);
            log.info("[End UserController - Delete User with userid " + userId + "]");
            return new ResponseEntity<>("User Deleted Successfully", OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DELETE_USER_FAIL");
        }
    }

    @PutMapping("/change-password")
    @ApiOperation(value = "Required login again when change pw successfully")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        log.info("[Start UserController - Change Password with username " + userCommon.getCurrentUser().getUsername() + "]");
        try {
            iUserService.changePassword(changePasswordRequest);
            log.info("[End UserController - Change Password with username " + userCommon.getCurrentUser().getUsername() + "]");
            return new ResponseEntity<>("Password Change Successfully", OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "UPDATE_PASSWORD_FAIL");
        }
    }

    @GetMapping("/list-role")
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        log.info("[Start UserController - Get All Roles]");
        List<RoleDto> roleDto = iUserService.getAllRoles();
        log.info("[End UserController - Get All Roles]");
        return status(HttpStatus.OK).body(roleDto);
    }

}
