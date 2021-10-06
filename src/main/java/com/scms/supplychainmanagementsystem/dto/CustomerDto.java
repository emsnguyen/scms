package com.scms.supplychainmanagementsystem.dto;

import com.scms.supplychainmanagementsystem.entity.Customer;
import com.scms.supplychainmanagementsystem.entity.District;
import com.scms.supplychainmanagementsystem.entity.User;
import com.scms.supplychainmanagementsystem.entity.Warehouse;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Instant;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

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

    public CustomerDto(Customer entity) {
        this.customerId = entity.getCustomerId();
        this.customerCode = entity.getCustomerCode();
        CustomerType = entity.getCustomerType();
        this.customerName = entity.getCustomerName();
        this.warehouseId = entity.getWarehouse().getWarehouseID();
        this.email = entity.getEmail();
        this.phone = entity.getPhone();
        DateOfBirth = entity.getDateOfBirth();
        Gender = entity.getGender();
        TaxCode = entity.getTaxCode();
        Facebook = entity.getFacebook();
        this.districtId = entity.getDistrict().getDistrictID();
        this.streetAddress = entity.getStreetAddress();
        CompanyName = entity.getCompanyName();
        Note = entity.getNote();
        this.createdDate = entity.getCreatedDate();
        this.createdBy = entity.getCreatedBy().getUsername().toString();
        this.lastModifiedBy = entity.getCreatedBy().getUsername().toString();
    }
}
