package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.PurchaseDetailDto;
import com.scms.supplychainmanagementsystem.dto.PurchaseDto;
import com.scms.supplychainmanagementsystem.entity.Purchase;
import com.scms.supplychainmanagementsystem.entity.PurchaseDetails;
import com.scms.supplychainmanagementsystem.service.IPurchaseDetailService;
import com.scms.supplychainmanagementsystem.service.IPurchaseService;
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
@RequestMapping("/api/manage/purchasedetail")
public class PurchaseDetailController {

    private IPurchaseDetailService iPurchaseDetailService;


    @GetMapping("/purchase/{purchaseid}")
    public ResponseEntity<Map<String, Object>> getAllPurchaseDetail(@PathVariable  Long purchaseid,
                                                              @RequestParam(required = false) Long warehouseId,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        log.info("[Start PurchaseController - Get All Purchase In Warehouse]");
        List<PurchaseDetails> PurchaselList;
        Page<PurchaseDetails> PurchasePage;
        Pageable pageable = PageRequest.of(page, size);

        PurchasePage = iPurchaseDetailService.getAllPurchaseDetail(purchaseid,warehouseId, pageable);

        PurchaselList = PurchasePage.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("data", PurchaselList);
        response.put("currentPage", PurchasePage.getNumber());
        response.put("totalItems", PurchasePage.getTotalElements());
        response.put("totalPages", PurchasePage.getTotalPages());
        if (!PurchasePage.isEmpty()) {
            response.put("message", HttpStatus.OK);
        } else {
            response.put("message", "EMPTY_RESULT");
        }
        log.info("[End CustomerController - Get All Customer In Warehouse]");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }





    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> createPurchaseDetail(@Valid @RequestBody PurchaseDetailDto purchaseDetailDto) {
        log.info("[Start PurchaseDetailController -  createPurchaseDetail  ]");
        iPurchaseDetailService.savePurchaseDetail(purchaseDetailDto);
        log.info("[End PurchaseDetailController -  createPurchaseDetail  ]");
        return new ResponseEntity<>("Created Successfully", CREATED);
    }

    @GetMapping("/{purchasedetailid}")
    public ResponseEntity<PurchaseDetailDto> getPurchaseidById(@PathVariable Long purchasedetailid) {
        log.info("[Start PurchaseDetailController - Get PurchaseDetail By ID]");
        PurchaseDetails purchaseDetails= iPurchaseDetailService.getPurchaseDetailByIdInWarehouse(purchasedetailid);
        if (purchaseDetails != null) {
            PurchaseDetailDto purchaseDetailDto = new PurchaseDetailDto(purchaseDetails);
            log.info("[End PurchaseController - Get Purchase By ID]");
            return status(HttpStatus.OK).body(purchaseDetailDto);
        } else {
            return null;
        }
    }

    @PutMapping("/{purchasedetailid}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> updatePurchase(@PathVariable Long purchasedetailid, @Valid @RequestBody PurchaseDetailDto purchaseDetailDto) {
        log.info("[Start PurchaseController - Update Purchase ]");
        iPurchaseDetailService.updatePurchaseDetail(purchasedetailid, purchaseDetailDto);
        log.info("[End PurchaseController - Update Purchase ]");
        return new ResponseEntity<>("Update Purchase Successfully", OK);
    }

    @DeleteMapping("/{purchasedetailid}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> DeletePurchase(@PathVariable Long purchasedetailid) {
        log.info("[Start PurchaseController - Delete Purchase By ID]");
        iPurchaseDetailService.deletePurchaseDetail(purchasedetailid);
        log.info("[End PurchaseController - Delete Purchase By ID]");
        return new ResponseEntity<>("Delete Purchase Successfully", OK);
    }
}