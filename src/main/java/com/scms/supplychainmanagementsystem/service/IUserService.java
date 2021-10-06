package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.ChangePasswordRequest;
import com.scms.supplychainmanagementsystem.dto.RoleDto;
import com.scms.supplychainmanagementsystem.dto.UserDto;
import com.scms.supplychainmanagementsystem.entity.User;

import java.util.List;

public interface IUserService {
    void updateUser(UserDto UserDto);

    void saveUser(UserDto UserDto);

    void deleteUser(Long userId);

    User findUserById(Long userId);

    void changePassword(ChangePasswordRequest changePasswordRequest);

    List<RoleDto> getAllRoles();
}
