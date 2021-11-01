package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.WarehouseDto;
import com.scms.supplychainmanagementsystem.entity.Warehouse;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.WarehouseRepository;
import com.scms.supplychainmanagementsystem.service.IWarehouseService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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
@RequestMapping("/api/manage/warehouse")

public class WarehouseController {
    private IWarehouseService iWarehouseService;
    private WarehouseRepository warehouseRepository;


    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllWarehouse(@RequestParam(required = false) String warehouseName,
                                                               @RequestParam(required = false) String address,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        log.info("[Start WarehouselController - Get All  Warehouse]");
        List<Warehouse> warehouseList;
        Page<Warehouse> warehousePage;
        Pageable pageable = PageRequest.of(page, size);

        warehousePage = iWarehouseService.getAllWarehouse(warehouseName, address, pageable);

        warehouseList = warehousePage.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("data", warehouseList);
        response.put("currentPage", warehousePage.getNumber());
        response.put("totalItems", warehousePage.getTotalElements());
        response.put("totalPages", warehousePage.getTotalPages());
        if (!warehousePage.isEmpty()) {
            response.put("message", HttpStatus.OK);
        } else {
            response.put("message", "EMPTY_RESULT");
        }
        log.info("[End WarehouselController - Get All Warehouse]");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> createWarehouse(@Valid @RequestBody WarehouseDto warehouseDto) {
        log.info("[Start WarehouselController -  createWarehouse " + warehouseDto.getWarehouseName() + "]");
        iWarehouseService.saveWarehouse(warehouseDto);
        log.info("[End WarehouselController -  createWarehouse " + warehouseDto.getWarehouseName() + "]");
        return new ResponseEntity<>("Created Successfully", CREATED);
    }

    @GetMapping("/{materialId}")
    public ResponseEntity<WarehouseDto> getWareHouseById(@PathVariable Long warehouseId) {
        log.info("[Start WarehouselController - Get Warehouse By ID]");
        Warehouse warehouse = warehouseRepository.getById(warehouseId);
        if (warehouse != null) {
            WarehouseDto warehouseDto = new WarehouseDto(warehouse);
            log.info("[End WarehouselController - Get Warehouse By ID]");
            return status(HttpStatus.OK).body(warehouseDto);
        } else {
            return null;
        }
    }

    @PutMapping("/{warehoseId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> updateMaterial(@PathVariable Long warehouseId, @Valid @RequestBody WarehouseDto warehouseDto) {
        log.info("[Start WarehouselController - Update Warehouse with name " + warehouseDto.getWarehouseName() + "]");
        iWarehouseService.updateWarehouse(warehouseId, warehouseDto);
        log.info("[End WarehouselController - Update Warehouse with name " + warehouseDto.getWarehouseName() + "]");
        return new ResponseEntity<>("Update Warehouse Successfully", OK);
    }

    @DeleteMapping("/{warehoseId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> DeleteWarehouse(@PathVariable Long warehoseId) {
        log.info("[Start WarehouselController - Get Warehouse By ID]");
        try {
            iWarehouseService.deleteWarehouse(warehoseId);
        } catch (DataIntegrityViolationException e) {
            throw new AppException("Some resource using this data");
        }
        log.info("[End WarehouselController - Get Warehouse By ID]");
        return new ResponseEntity<>("Delete Warehouse Successfully", OK);
    }
}
