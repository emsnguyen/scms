package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.UserDto;

public interface IUserService {
    void updateUser(UserDto UserDto);

    void saveUser(UserDto UserDto);
}
