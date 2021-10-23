package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.PurchaseDto;
import com.scms.supplychainmanagementsystem.dto.PurchaseHistoryDto;
import com.scms.supplychainmanagementsystem.entity.Purchase;
import com.scms.supplychainmanagementsystem.entity.PurchaseHistory;
import com.scms.supplychainmanagementsystem.service.IPurchaseHistory;
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
@RequestMapping("/api/manage/purchase")
public class PurchaseController {

    private IPurchaseService iPurchaseService;


    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPurchase(@RequestParam(required = false) Long warehouseId,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        log.info("[Start PurchaseController - Get All Purchase In Warehouse]");
        List<Purchase> PurchaselList;
        Page<Purchase> PurchasePage;
        Pageable pageable = PageRequest.of(page, size);

        PurchasePage = iPurchaseService.getAllPurchase(warehouseId, pageable);

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
    public ResponseEntity<String> createPurchase(@Valid @RequestBody PurchaseDto purchaseDto) {
        log.info("[Start PurchaseController -  createPurchase  ]");
        iPurchaseService.savePurchase(purchaseDto);
        log.info("[End PurchaseController -  createPurchase  ]");
        return new ResponseEntity<>("Created Successfully", CREATED);
    }

    @GetMapping("/{purchaseid}")
    public ResponseEntity<Purchase> getPurchaseidById(@PathVariable Long purchaseid) {
        log.info("[Start PurchaseController - Get Purchase By ID]");
        Purchase purchase = iPurchaseService.getPurchaseByIdInWarehouse(purchaseid);
        if (purchase != null) {
            PurchaseDto purchaseDto = new PurchaseDto(purchase);
            log.info("[End PurchaseController - Get Purchase By ID]");
            return status(HttpStatus.OK).body(purchase);
        } else {
            return null;
        }
    }

    @PutMapping("/{purchaseid}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> updatePurchase(@PathVariable Long purchaseid, @Valid @RequestBody PurchaseDto purchaseDto) {
        log.info("[Start PurchaseController - Update Purchase ]");
        iPurchaseService.updatePurchase(purchaseid, purchaseDto);
        log.info("[End PurchaseController - Update Purchase ]");
        return new ResponseEntity<>("Update Purchase Successfully", OK);
    }

    @DeleteMapping("/{purchaseid}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> DeletePurchase(@PathVariable Long purchaseid) {
        log.info("[Start PurchaseController - Delete Purchase By ID]");
        iPurchaseService.deletePurchase(purchaseid);
        log.info("[End PurchaseController - Delete Purchase By ID]");
        return new ResponseEntity<>("Delete Purchase Successfully", OK);
    }
}
