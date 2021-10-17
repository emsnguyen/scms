package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.ContactDeliveryDto;
import com.scms.supplychainmanagementsystem.entity.ContactDelivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IContactDeliveryService {
    Page<ContactDelivery> getAllContactDeliveryofcustomer(Long customerId, String contactname, Pageable pageble);

    ContactDelivery getContactDeliveryById(Long contactDeliveryId);

    void updateIContactDelivery(Long contactDeliveryId, ContactDeliveryDto contactDeliveryDto);

    void saveContactDelivery(ContactDeliveryDto contactDeliveryDto);

    void deleteContactDelivery(Long contactDeliveryId);
}
