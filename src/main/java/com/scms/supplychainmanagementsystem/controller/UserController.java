package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.service.IAuthService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final IAuthService iAuthService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Returns the current user profile. Requires ADMIN Access")
    public ResponseEntity getUserProfile() {
        String currentName = iAuthService.getCurrentUser().getUsername();
        String currentUserRole = iAuthService.getCurrentUser().getRole().getRoleName();
        log.info(currentName + " has role: " + currentUserRole);
        return ResponseEntity.ok("Hello [" + currentUserRole + "] " + currentName);
    }
}
