package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.MaterialDto;
import com.scms.supplychainmanagementsystem.dto.SupplierDto;
import com.scms.supplychainmanagementsystem.entity.Material;
import com.scms.supplychainmanagementsystem.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISupplierService {

    Page<Supplier> getAllSupplier(String suppliername, Long warehouseId, Pageable pageble);

    Supplier getSupplierByIdInWarehouse(Long supplierId);

    void updateSupplier(Long supplierId, SupplierDto supplierDto);

    void saveSupplier(SupplierDto supplierDto);

    void deleteSupplier(Long supplierDto);

}
