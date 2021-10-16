package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.PurchaseHistoryDto;
import com.scms.supplychainmanagementsystem.entity.PurchaseHistory;
import com.scms.supplychainmanagementsystem.service.IPurchaseHistory;
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
public class PurchaseHistoryController {

    private IPurchaseHistory iPurchaseHistory;


    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPurchase(@RequestParam(required = false) Long warehouseId,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        log.info("[Start PurchaseHistoryController - Get All PurchaseHistory In Warehouse]");
        List<PurchaseHistory> PurchaselList;
        Page<PurchaseHistory> PurchasePage;
        Pageable pageable = PageRequest.of(page, size);

        PurchasePage = iPurchaseHistory.getAllPurchaseHistory(warehouseId, pageable);

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
    public ResponseEntity<String> createPurchase(@Valid @RequestBody PurchaseHistoryDto purchaseHistoryDto) {
        log.info("[Start PurchaseHistoryController -  createPurchaseHistory  ]");
        iPurchaseHistory.savePurchaseHistory(purchaseHistoryDto);
        log.info("[End PurchaseHistoryController -  createPurchaseHistory  ]");
        return new ResponseEntity<>("Created Successfully", CREATED);
    }

    @GetMapping("/{purchaseid}")
    public ResponseEntity<PurchaseHistoryDto> getPurchaseidById(@PathVariable Long purchaseid) {
        log.info("[Start PurchaseHistoryController - Get PurchaseHistory By ID]");
        PurchaseHistory purchaseHistory = iPurchaseHistory.getPurchaseHistoryByIdInWarehouse(purchaseid);
        if (purchaseHistory != null) {
            PurchaseHistoryDto purchaseHistoryDto = new PurchaseHistoryDto(purchaseHistory);
            log.info("[End PurchaseHistoryController - Get PurchaseHistory By ID]");
            return status(HttpStatus.OK).body(purchaseHistoryDto);
        } else {
            return null;
        }
    }

    @PutMapping("/{purchaseid}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> updatePurchase(@PathVariable Long purchaseid, @Valid @RequestBody PurchaseHistoryDto purchaseHistoryDto) {
        log.info("[Start PurchaseHistoryController - Update PurchaseHistory with name ]");
        iPurchaseHistory.updatePurchaseHistory(purchaseid, purchaseHistoryDto);
        log.info("[End PurchaseHistoryController - Update PurchaseHistory with name ]");
        return new ResponseEntity<>("Update PurchaseHistory Successfully", OK);
    }

    @DeleteMapping("/{purchaseid}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> DeletePurchase(@PathVariable Long purchaseid) {
        log.info("[Start PurchaseHistoryController - Get PurchaseHistory By ID]");
        iPurchaseHistory.deletePurchaseHistory(purchaseid);
        log.info("[End PurchaseHistoryController - Get PurchaseHistory By ID]");
        return new ResponseEntity<>("Delete PurchaseHistory Successfully", OK);
    }
}
