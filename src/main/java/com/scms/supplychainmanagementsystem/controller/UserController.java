package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.UserDto;
import com.scms.supplychainmanagementsystem.entity.User;
import com.scms.supplychainmanagementsystem.service.IUserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        UserDto user = UserDto.builder()
                .username(currentUser.getUsername())
                .email(currentUser.getEmail())
                .roleId(currentUser.getRole().getRoleID())
                .warehouseId(currentUser.getWarehouse().getWarehouseID())
                .firstName(currentUser.getFirstName())
                .lastName(currentUser.getLastName())
                .isActive(currentUser.isActive())
                .phone(currentUser.getPhone())
                .dateOfBirth(currentUser.getDateOfBirth())
                .districtId(currentUser.getDistrict().getDistrictID())
                .streetAddress(currentUser.getStreetAddress())
                .createdDate(currentUser.getCreatedDate())
                .createdBy(currentUser.getUsername())
                .build();
        log.info("[End UserController - Get User Profile]");
        return status(HttpStatus.OK).body(user);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
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
        // TODO:
        UserDto user = new UserDto();
        log.info("[End UserController - Get User By User ID]");
        return status(HttpStatus.OK).body(user);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @Valid @RequestBody UserDto userDto) {
        log.info("[Start UserController - Update User with username " + userDto.getUsername() + "]");
        // TODO:
        log.info("[End UserController - Update User with username " + userDto.getUsername() + "]");
        return new ResponseEntity<>("User Updated Successfully", OK);
    }

}
