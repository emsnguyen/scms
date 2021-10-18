package com.scms.supplychainmanagementsystem.common;

import com.scms.supplychainmanagementsystem.entity.User;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class UserCommon {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal
                = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    public boolean checkAccessUserInfoInWarehouse(Long userId) {
        User u = userRepository.findById(userId).orElseThrow(() -> new AppException("User not found"));
        User current = getCurrentUser();
        if (current.getRole().getRoleID() == 1) {
            return true;
        }
        if (u.getRole() == null || u.getDistrict() == null) {
            throw new AppException("Not fill in all required fields");
        } else {
            return u.getWarehouse().getWarehouseID().equals(current.getWarehouse().getWarehouseID());
        }
    }
}
