package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.ChangePasswordRequest;
import com.scms.supplychainmanagementsystem.dto.RoleDto;
import com.scms.supplychainmanagementsystem.dto.UserDto;

import java.util.List;

public interface IUserService {
    void updateUser(UserDto UserDto);

    void saveUser(UserDto UserDto);

    void deleteUser(Long userId);

    UserDto getUserById(Long userId);

    void changePassword(ChangePasswordRequest changePasswordRequest);

    List<RoleDto> getAllRoles();
}
