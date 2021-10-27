package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.MaterialDto;
import com.scms.supplychainmanagementsystem.dto.WarehouseDto;
import com.scms.supplychainmanagementsystem.entity.Material;
import com.scms.supplychainmanagementsystem.entity.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IWarehouseService {
    Page<Warehouse> getAllWarehouse(String warehouseName, String address, Pageable pageble);

    void updateWarehouse(Long warehouseId, WarehouseDto warehouseDto);

    void saveWarehouse(WarehouseDto warehouseDto);

    void deleteWarehouse(Long warehouseId);

}
