package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.dto.InventoryDto;
import com.scms.supplychainmanagementsystem.entity.Inventory;
import com.scms.supplychainmanagementsystem.service.IInventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class InventoryService implements IInventory {
    @Override
    public Page<Inventory> getAllInventory(String Productname, Long warehouseId, Pageable pageble) {
        return null;
    }

    @Override
    public Inventory getInventoryByIdInWarehouse(Long InventoryId) {
        return null;
    }

    @Override
    public void updateInventory(Long inventoryId, InventoryDto inventoryDto) {

    }

    @Override
    public void saveInventory(InventoryDto inventoryDto) {

    }

    @Override
    public void deleteInventory(Long InventoryId) {

    }
}
