package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.InventoryDto;
import com.scms.supplychainmanagementsystem.entity.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IInventoryService {
    Page<Inventory> getAllInventory(String Productname, Long warehouseId, Pageable pageble);

    Inventory getInventoryByIdInWarehouse(Long InventoryId);

    void updateInventory(Long inventoryId, InventoryDto inventoryDto);

    void saveInventory(InventoryDto inventoryDto);

    void deleteInventory(Long InventoryId);
}
