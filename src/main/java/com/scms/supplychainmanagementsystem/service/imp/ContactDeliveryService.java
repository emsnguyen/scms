package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.ContactDeliveryDto;
import com.scms.supplychainmanagementsystem.entity.*;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.ContactDeliveryRepository;
import com.scms.supplychainmanagementsystem.repository.CustomerRepository;
import com.scms.supplychainmanagementsystem.service.IContactDeliveryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@AllArgsConstructor
@Transactional
@Slf4j
@Service
public class ContactDeliveryService implements IContactDeliveryService {
    private final UserCommon userCommon;
    private ContactDeliveryRepository contactDeliveryRepository;
    private CustomerRepository customerRepository;


    @Override
    public Page<ContactDelivery> getAllContactDeliveryofcustomer(Long customerId, String contactname, Pageable pageable) {
        log.info("[Start ContactDeliveryService - Get All ContactDelivery]");
        Page<ContactDelivery> contactPage;
        User current = userCommon.getCurrentUser();
        Warehouse wh = current.getWarehouse();
        Long userId = current.getUserId();
        if (current.getRole().getRoleID() == 1) {
            contactPage = contactDeliveryRepository.filterAllCustomer(customerId, contactname, pageable);
        } else {
            if (current.getWarehouse().getWarehouseID() == customerRepository.findByCustomerId(customerId).getWarehouse().getWarehouseID()) {
                contactPage = contactDeliveryRepository.filterInOneCustomer(customerId, contactname, pageable);
            } else {
                throw new AppException("this Customer have no Contact");
//                contactPage = contactDeliveryRepository.filterInOneCustomer(, contactname, pageable);
            }

        }
        log.info("[End ContactDeliveryService - Get All ContactDelivery]");
        return contactPage;
    }


    @Override
    public ContactDelivery getContactDeliveryById(Long contactDeliveryId) {
        User currentUser = userCommon.getCurrentUser();
        ContactDelivery contactDelivery = new ContactDelivery();
        if (currentUser.getRole().getRoleID() != 1) {
            contactDelivery = contactDeliveryRepository.findByContactIDManager(contactDeliveryId);
            if (contactDelivery == null || currentUser.getWarehouse().getWarehouseID() != contactDelivery.getCustomer().getWarehouse().getWarehouseID()) {
                throw new AppException("Not Found");
            }

        } else {
            contactDelivery = contactDeliveryRepository.findByContactIDAdmin(contactDeliveryId);
        }
        return contactDelivery;
    }

    @Override
    public void updateIContactDelivery(Long contactDeliveryId, ContactDeliveryDto contactDeliveryDto) {
        log.info("[Start ContactDeliveryService - UpdateContact with ContactName: " + contactDeliveryDto.getContactName() + "]");
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");
        Customer customer = new Customer();
        customer.setCustomerId(contactDeliveryDto.getCustomerId());

        if (currentUser.getRole().getRoleID() != 1) {

            if (currentUser.getWarehouse().getWarehouseID() != contactDeliveryRepository.findByContactIDAdmin(contactDeliveryId).getCustomer().getWarehouse().getWarehouseID()) {
                throw new AppException("you cant update in another Warehouse");
            }
        }

        ContactDelivery contactDelivery = ContactDelivery.builder()
                .contactID(contactDeliveryId)
                .contactName(contactDeliveryDto.getContactName())
                .address(contactDeliveryDto.getAddress())
                .createedDate(Instant.now())
                .customer(customer)
                .createdBy(currentUser)
                .district(District.builder().districtID(contactDeliveryDto.getDistrictId()).build())
                .phone(contactDeliveryDto.getPhone())
                .email(contactDeliveryDto.getEmail())
                .build();
        log.info("[Start update ContactDelivery " + contactDeliveryDto.getContactName() + " to database]");
        contactDeliveryRepository.save(contactDelivery);
        log.info("[End update ContactDelivery " + contactDeliveryDto.getContactName() + " to database]");
        log.info("[End ContactDeliveryService - UpdateContact with ContactName: " + contactDeliveryDto.getContactName() + "]");
    }

    @Override
    public void saveContactDelivery(ContactDeliveryDto contactDeliveryDto) {
        log.info("[Start ContactDeliveryService - saveContact with ContactName: " + contactDeliveryDto.getContactName() + "]");
//        if (materialRepository.existsByMaterialName(materialDto.getMaterialName())) {
//            throw new AppException("name exists");
//        }
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");

        Customer customer = new Customer();
        customer.setCustomerId(contactDeliveryDto.getCustomerId());

        ContactDelivery contactDelivery = ContactDelivery.builder()
                .contactName(contactDeliveryDto.getContactName())
                .address(contactDeliveryDto.getAddress())
                .createedDate(Instant.now())
                .customer(customer)
                .createdBy(currentUser)
                .district(District.builder().districtID(contactDeliveryDto.getDistrictId()).build())
                .phone(contactDeliveryDto.getPhone())
                .email(contactDeliveryDto.getEmail())
                .build();
        log.info("[Start save ContactDelivery " + contactDeliveryDto.getContactName() + " to database]");
        contactDeliveryRepository.saveAndFlush(contactDelivery);
        log.info("[End save ContactDelivery " + contactDeliveryDto.getContactName() + " to database]");
        log.info("[End ContactDeliveryService - saveContactDelivery with ContactName: " + contactDeliveryDto.getContactName() + "]");
    }

    @Override
    public void deleteContactDelivery(Long contactDeliveryId) {
        User currentUser = userCommon.getCurrentUser();
        if (currentUser.getRole().getRoleID() != 1) {
            if (currentUser.getWarehouse().getWarehouseID() != contactDeliveryRepository.findByContactIDAdmin(contactDeliveryId).getCustomer().getWarehouse().getWarehouseID()) {
                throw new AppException("you cant Delete in another Warehouse");
            }
            contactDeliveryRepository.deleteContactDeliverie(contactDeliveryId);
        } else {
            contactDeliveryRepository.deleteContactDeliverie(contactDeliveryId);
        }
    }
}
