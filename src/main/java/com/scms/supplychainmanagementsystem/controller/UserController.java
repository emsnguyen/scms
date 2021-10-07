package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.ChangePasswordRequest;
import com.scms.supplychainmanagementsystem.dto.RoleDto;
import com.scms.supplychainmanagementsystem.dto.UserDto;
import com.scms.supplychainmanagementsystem.entity.User;
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
    public ResponseEntity<UserDto> getUserProfile() {
        log.info("[Start UserController - Get User Profile]");
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
    public ResponseEntity<Map<String, Object>> getAllUsersInWarehouse(@RequestParam String username,
                                                                      @RequestParam String roleName,
                                                                      @RequestParam(defaultValue = "1") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {
        log.info("[Start UserController - Get All Users In Warehouse]");
        try {
            List<User> userList;
            Page<User> userPage;
            Pageable pageable = PageRequest.of(page, size);

            userPage = iUserService.getAllUsers(pageable);
            userList = userPage.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("data", userList);
            response.put("currentPage", userPage.getNumber());
            response.put("totalItems", userPage.getTotalElements());
            response.put("totalPages", userPage.getTotalPages());
            response.put("message", HttpStatus.OK);
            log.info("[End UserController - Get All Users In Warehouse]");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
