package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.InventoryDto;
import com.scms.supplychainmanagementsystem.dto.StockHistoryDto;
import com.scms.supplychainmanagementsystem.entity.InvProductStatus;
import com.scms.supplychainmanagementsystem.entity.Inventory;
import com.scms.supplychainmanagementsystem.entity.StockHistory;
import com.scms.supplychainmanagementsystem.repository.InvProductStatusRepository;
import com.scms.supplychainmanagementsystem.service.IInventoryService;
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
@RequestMapping("/api/inventory")
public class InventoryController {

    private IInventoryService iInventoryService;
    private InvProductStatusRepository invProductStatusRepository;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> createInventory(@Valid @RequestBody InventoryDto inventoryDto) {
        log.info("[Start InventoryController -  createInventory  ]");
        iInventoryService.saveInventory(inventoryDto);
        log.info("[End InventoryController -  createInventory  ]");
        return new ResponseEntity<>("Created Successfully", CREATED);
    }

    @GetMapping()
    public ResponseEntity<Map<String, Object>> getAllInventory(@RequestParam (required = false) String productName,
                                                                  @RequestParam(required = false) Long warehouseId,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {
        log.info("[Start InventoryController - Get All Inventory In Warehouse]");
        List<Inventory> InventoryList;
        Page<Inventory> InventoryPage;
        Pageable pageable = PageRequest.of(page, size);

        InventoryPage = iInventoryService.getAllInventory(productName,warehouseId, pageable);

        InventoryList = InventoryPage.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("data", InventoryList);
        response.put("currentPage", InventoryPage.getNumber());
        response.put("totalItems", InventoryPage.getTotalElements());
        response.put("totalPages", InventoryPage.getTotalPages());
        if (!InventoryPage.isEmpty()) {
            response.put("message", HttpStatus.OK);
        } else {
            response.put("message", "EMPTY_RESULT");
        }
        log.info("[End InventoryController - Get All Inventory In Warehouse]");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/{inventoryId}")
    public ResponseEntity<InventoryDto> getInventoryById(@PathVariable Long inventoryId) {
        log.info("[Start InventoryController - Get Inventory By ID]");
        Inventory inventory = iInventoryService.getInventoryByIdInWarehouse(inventoryId);
        if (inventory != null) {
            InventoryDto inventoryDto = new InventoryDto(inventory);
            log.info("[End InventoryController - Get Inventory By ID]");
            return status(HttpStatus.OK).body(inventoryDto);
        } else {
            return null;
        }
    }

    @PutMapping("/{inventoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> updateInventory(@PathVariable Long inventoryId, @Valid @RequestBody InventoryDto inventoryDto) {
        log.info("[Start InventoryController - Update Inventory ]");
        iInventoryService.updateInventory(inventoryId, inventoryDto);
        log.info("[End InventoryController - Update Inventory ]");
        return new ResponseEntity<>("Update Inventory Successfully", OK);
    }

    @DeleteMapping("/{inventoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> DeleteInventory(@PathVariable Long inventoryId) {
        log.info("[Start InventoryController - Delete Inventory By ID]");
        iInventoryService.deleteInventory(inventoryId);
        log.info("[End InventoryController - Delete Inventory By ID]");
        return new ResponseEntity<>("Delete Inventory Successfully", OK);
    }
    @GetMapping("/liststatus")
    public ResponseEntity<List<InvProductStatus>> getAllStatus() {
        log.info("[Start InventoryController - Get Inventory By ID]");
        List<InvProductStatus> list =invProductStatusRepository.findAll();
        return status(HttpStatus.OK).body(list);
    }
}
