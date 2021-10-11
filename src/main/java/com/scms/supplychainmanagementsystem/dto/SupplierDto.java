package com.scms.supplychainmanagementsystem.dto;

import com.scms.supplychainmanagementsystem.entity.District;
import com.scms.supplychainmanagementsystem.entity.Supplier;
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

    public SupplierDto(Supplier supplier) {
        this.supplierId = supplier.getSupplierId();
        SupplierCode = supplier.getSupplierCode();
        SupplierName = supplier.getSupplierName();
        this.email = supplier.getEmail();
        this.phone = supplier.getPhone();
        this.streetAddress = supplier.getStreetAddress();
        this.isActive = supplier.getIsActive();
        this.warehouseId = supplier.getWarehouse().getWarehouseID();
        if(supplier.getDistrict()!=null){
        this.districtId = supplier.getDistrict().getDistrictID();}
        this.createdBy = supplier.getCreatedBy().getUsername();
        this.lastModifiedBy = supplier.getLastModifiedBy().getUsername();
    }
}
