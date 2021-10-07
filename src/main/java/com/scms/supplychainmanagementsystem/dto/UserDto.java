package com.scms.supplychainmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private Long districtId;

    private String streetAddress;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

}
