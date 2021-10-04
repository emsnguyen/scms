package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.UserRequest;

public interface IUserService {
    void updateUser(UserRequest userRequest);

    void saveUser(UserRequest userRequest);
}
