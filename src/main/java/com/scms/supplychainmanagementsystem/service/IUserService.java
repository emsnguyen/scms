package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.UserDto;
import com.scms.supplychainmanagementsystem.entity.User;

public interface IUserService {
    void updateUser(UserDto UserDto);

    void saveUser(UserDto UserDto);

    void deleteUser(Long userId);

    User findUserById(Long userId);
}
