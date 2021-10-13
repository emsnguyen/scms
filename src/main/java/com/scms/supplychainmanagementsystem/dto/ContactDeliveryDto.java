package com.scms.supplychainmanagementsystem.dto;

import com.scms.supplychainmanagementsystem.entity.ContactDelivery;
import com.scms.supplychainmanagementsystem.entity.Customer;
import com.scms.supplychainmanagementsystem.entity.District;
import com.scms.supplychainmanagementsystem.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDeliveryDto {

    private Long contactID;

    private Long customerId;

    private String contactName;

    private String email;

    private String phone;

    private String address;

    private Instant createdDate;

    private Long districtId;

    private String createdBy;

    public ContactDeliveryDto(ContactDelivery contactDelivery) {
        this.contactID = contactDelivery.getContactID();
        this.customerId = contactDelivery.getCustomer().getCustomerId();
        this.contactName = contactDelivery.getContactName();
        this.email = contactDelivery.getEmail();
        this.phone = contactDelivery.getPhone();
        this.address = contactDelivery.getAddress();
        this.createdDate = contactDelivery.getCreateedDate();
        this.districtId = contactDelivery.getDistrict().getDistrictID();
        this.createdBy = contactDelivery.getCreatedBy().getUsername();
    }
}
