package com.scms.supplychainmanagementsystem.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long userId;

    private String username;

    private String email;

    private Long roleId;

    private Long warehouseId;

    private String firstName;

    private String lastName;

    private boolean isActive;

    private String phone;

    private Instant dateOfBirth;

    private Long districtId;

    private String streetAddress;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

}
