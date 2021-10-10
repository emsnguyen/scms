package com.scms.supplychainmanagementsystem.dto;

import com.scms.supplychainmanagementsystem.entity.District;
import com.scms.supplychainmanagementsystem.entity.User;
import com.scms.supplychainmanagementsystem.entity.Warehouse;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierDto {
    private Long supplierId;

    private String SupplierCode;

    private String SupplierName ;

    private String email ;

    private String phone ;

    private String streetAddress ;

    private Boolean isActive;

    private Long warehouseId;

    private Long districtId;

    private String createdBy;

    private String lastModifiedBy;
}
