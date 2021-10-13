package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.ContactDeliveryDto;
import com.scms.supplychainmanagementsystem.dto.MaterialDto;
import com.scms.supplychainmanagementsystem.entity.ContactDelivery;
import com.scms.supplychainmanagementsystem.entity.Material;
import com.scms.supplychainmanagementsystem.service.IContactDeliveryService;
import com.scms.supplychainmanagementsystem.service.IMaterialService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/manage/contact")
public class ContactDeliveryController {
    private IContactDeliveryService iContactDeliveryService;


    @GetMapping("/customer/{customerid}")
    public ResponseEntity<Map<String, Object>> getAllContact(@PathVariable(required = false) Long customerid,
                                                                         @RequestParam(required = false) String contactname,
                                                                         @RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size) {
        log.info("[Start ContactdeliveryController - Get All Contact]");
        List<ContactDelivery> contactList;
        Page<ContactDelivery> contactPage;
        Pageable pageable = PageRequest.of(page, size);

        contactPage = iContactDeliveryService.getAllContactDeliveryofcustomer(customerid,contactname, pageable);

        contactList = contactPage.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("data", contactList);
        response.put("currentPage", contactPage.getNumber());
        response.put("totalItems", contactPage.getTotalElements());
        response.put("totalPages", contactPage.getTotalPages());
        if (!contactPage.isEmpty()) {
            response.put("message", HttpStatus.OK);
        } else {
            response.put("message", "EMPTY_RESULT");
        }
        log.info("[End ContactDeliveryController - Get All Contact]");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> createContact(@Valid @RequestBody ContactDeliveryDto contactDeliveryDto) {
        log.info("[Start ContactDeliveryController -  createContact " + contactDeliveryDto.getContactName() + "]");
        iContactDeliveryService.saveContactDelivery(contactDeliveryDto);
        log.info("[End ContactDeliveryController -  createContact " + contactDeliveryDto.getContactName() + "]");
        return new ResponseEntity<>("Created Successfully", CREATED);
    }

    @GetMapping("/{contactid}")
    public ResponseEntity<ContactDeliveryDto> getContactById(@PathVariable Long contactid) {
        log.info("[Start ContactDeliveryController - Get Contact By ID]");
        ContactDelivery contactDelivery = iContactDeliveryService.getContactDeliveryById(contactid);
        if (contactDelivery != null) {
            ContactDeliveryDto contactDeliveryDto = new ContactDeliveryDto(contactDelivery);
            log.info("[End ContactDeliveryController - Get Contact By ID]");
            return status(HttpStatus.OK).body(contactDeliveryDto);
        } else {
            return null;
        }
    }

    @PutMapping("/{contactid}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> updateContact(@PathVariable Long contactid, @Valid @RequestBody ContactDeliveryDto contactDeliveryDto) {
        log.info("[Start ContactDeliveryController - Update Contact with name " + contactDeliveryDto.getContactName() + "]");
        iContactDeliveryService.updateIContactDelivery(contactid, contactDeliveryDto);
        log.info("[End ContactDeliveryController - Update Contact with name " + contactDeliveryDto.getContactName() + "]");
        return new ResponseEntity<>("Update Contact Successfully", OK);
    }

    @DeleteMapping("/{contactid}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> DeleteContact(@PathVariable Long contactid) {
        log.info("[Start ContactDeliveryController - delete Contact By ID]");
        iContactDeliveryService.deleteContactDelivery(contactid);
        log.info("[End ContactDeliveryController - delete Contact By ID]");
        return new ResponseEntity<>("Delete Contact Successfully", OK);
    }
}

