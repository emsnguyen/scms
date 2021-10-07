package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.ChangePasswordRequest;
import com.scms.supplychainmanagementsystem.dto.RoleDto;
import com.scms.supplychainmanagementsystem.dto.UserDto;
import com.scms.supplychainmanagementsystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    void updateUser(UserDto UserDto);

    void saveUser(UserDto UserDto);

    void deleteUser(Long userId);

    UserDto getUserById(Long userId);

    void changePassword(ChangePasswordRequest changePasswordRequest);

    List<RoleDto> getAllRoles();

    Page<User> getAllUsers(Pageable pageble);
}
