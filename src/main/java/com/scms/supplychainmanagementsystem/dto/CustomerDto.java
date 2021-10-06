package com.scms.supplychainmanagementsystem.dto;

import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {

    private Long customerId;

    private String customerCode;

    private String CustomerType;

    private String customerName;

    private Long warehouseId;

    private String email;

    private String phone;

    private LocalDate DateOfBirth;

    private Boolean Gender;

    private String TaxCode;

    private String Facebook;

    private Long districtId;

    private String streetAddress;

    private String CompanyName;

    private String Note;

    private Instant createdDate;

    private String createdBy;

    private String lastModifiedBy;
}
