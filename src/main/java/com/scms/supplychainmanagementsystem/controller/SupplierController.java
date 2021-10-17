package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.MaterialDto;
import com.scms.supplychainmanagementsystem.dto.SupplierDto;
import com.scms.supplychainmanagementsystem.entity.Material;
import com.scms.supplychainmanagementsystem.entity.Supplier;
import com.scms.supplychainmanagementsystem.service.IMaterialService;
import com.scms.supplychainmanagementsystem.service.ISupplierService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/manage/supplier")
public class SupplierController {
    private ISupplierService iSupplierService;


    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllSupplierInWarehouse(@RequestParam(required = false) String SupplierName,
                                                                         @RequestParam(required = false) Long warehouseId,
                                                                         @RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size) {
        log.info("[Start SupplierController - Get All Supplier In Warehouse]");
        List<Supplier> supplierlList;
        Page<Supplier> supplierPage;
        Pageable pageable = PageRequest.of(page, size);

        supplierPage = iSupplierService.getAllSupplier(SupplierName,warehouseId, pageable);

        supplierlList = supplierPage.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("data", supplierlList);
        response.put("currentPage", supplierPage.getNumber());
        response.put("totalItems", supplierPage.getTotalElements());
        response.put("totalPages", supplierPage.getTotalPages());
        if (!supplierPage.isEmpty()) {
            response.put("message", HttpStatus.OK);
        } else {
            response.put("message", "EMPTY_RESULT");
        }
        log.info("[End SupplierController - Get All Supplier In Warehouse]");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/warehouse/{warehouseid}")
    public ResponseEntity<List<Supplier>> getSupplierInWarehouse(@PathVariable Long warehouseid) {
        log.info("[Start SupplierController - Get PurchaseHistory By ID]");
        List<Supplier> supplierList = new ArrayList<>();
        supplierList=iSupplierService.getSupplierInWareHouse(warehouseid);
        return new ResponseEntity<>(supplierList, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> createSupplier(@Valid @RequestBody SupplierDto supplierDto) {
        log.info("[Start SupplierController -  createSupplier " + supplierDto.getSupplierName() + "]");
        iSupplierService.saveSupplier(supplierDto);
        log.info("[End SupplierController -  createSupplier " + supplierDto.getSupplierName() + "]");
        return new ResponseEntity<>("Created Successfully", CREATED);
    }

    @GetMapping("/{supplierid}")
    public ResponseEntity<SupplierDto> getMaterialByIdInWareHouse(@PathVariable Long supplierid) {
        log.info("[Start SupplierController - Get Supplier By ID]");
        Supplier supplier = iSupplierService.getSupplierByIdInWarehouse(supplierid);
        if (supplier != null) {
            SupplierDto supplierDto = new SupplierDto(supplier);
            log.info("[End SupplierController - Get Material By ID]");
            return status(HttpStatus.OK).body(supplierDto);
        } else {
            return null;
        }
    }

    @PutMapping("/{supplierId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> updateSupplier(@PathVariable Long supplierId, @Valid @RequestBody SupplierDto supplierDto) {
        log.info("[Start SupplierController - Update Supplier with name " + supplierDto.getSupplierName() + "]");
        iSupplierService.updateSupplier(supplierId, supplierDto);
        log.info("[End SupplierController - Update Supplier with name " + supplierDto.getSupplierName() + "]");
        return new ResponseEntity<>("Update Supplier Successfully", OK);
    }

    @DeleteMapping("/{supplierId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> DeleteSupplier(@PathVariable Long supplierId) {
        log.info("[Start SupplierController - Get Supplier By ID]");
        iSupplierService.deleteSupplier(supplierId);
        log.info("[End SupplierController - Get Supplier By ID]");
        return new ResponseEntity<>("Delete Supplier Successfully", OK);
    }
}
