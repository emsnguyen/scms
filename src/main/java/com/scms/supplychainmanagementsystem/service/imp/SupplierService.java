package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.SupplierDto;
import com.scms.supplychainmanagementsystem.entity.*;
import com.scms.supplychainmanagementsystem.repository.MaterialRepository;
import com.scms.supplychainmanagementsystem.repository.SupplierRepository;
import com.scms.supplychainmanagementsystem.service.ISupplierService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@AllArgsConstructor
@Transactional
@Slf4j
@Service
public class SupplierService implements ISupplierService {

    private SupplierRepository supplierRepository;
    private final UserCommon userCommon;

    @Override
    public Page<Supplier> getAllSupplier(String suppliername, Long warehouseId, Pageable pageable) {
        log.info("[Start SupplierService - Get All Supplier]");
        Page<Supplier> supplierPage;
        User current = userCommon.getCurrentUser();
        Warehouse wh = current.getWarehouse();
        Long userId = current.getUserId();
        if (current.getRole().getRoleID() == 1) {
            supplierPage = supplierRepository.filterAllWarehouses(suppliername, warehouseId, pageable);
        } else {
            supplierPage = supplierRepository.filterInOneWarehouse(suppliername, wh.getWarehouseID(), pageable);
        }
        log.info("[End CustomerService - Get All Customer]");
        return supplierPage;
    }

    @Override
    public Supplier getSupplierByIdInWarehouse(Long supplierId) {
        Supplier supplier = supplierRepository.findBySupplierIdAnhInWarehouse(supplierId, userCommon.getCurrentUser().getWarehouse().getWarehouseID());
        return supplier;
    }

    @Override
    public void updateSupplier(Long supplierId, SupplierDto supplierDto) {
        log.info("[Start SupplierService - saveSupplier with upplierName: " + supplierDto.getSupplierName() + "]");
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");

        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseID(currentUser.getWarehouse().getWarehouseID());

        Supplier supplier = Supplier.builder()
                .supplierId(supplierId)
                .SupplierCode(supplierDto.getSupplierCode())
                .SupplierName(supplierDto.getSupplierName())
                .email(supplierDto.getEmail())
                .isActive(supplierDto.getIsActive())
                .phone(supplierDto.getPhone())
                .warehouse(warehouse)
                .createdBy(currentUser)
                .lastModifiedBy(currentUser)
                .district(District.builder().districtID(supplierDto.getDistrictId()).build())
                .streetAddress(supplierDto.getStreetAddress())
                .build();
        log.info("[Start update Supplier " + supplier.getSupplierName() + " to database]");
        supplierRepository.save(supplier);
        log.info("[End update material " + supplier.getSupplierName() + " to database]");
        log.info("[End MaterialService - updateMaterial with name: " + supplier.getSupplierName() + "]");
    }

    @Override
    public void saveSupplier(SupplierDto supplierDto) {
        log.info("[Start MaterialService - saveMaterial with MaterialName: " + supplierDto.getSupplierName() + "]");
//        if (materialRepository.existsByMaterialName(materialDto.getMaterialName())) {
//            throw new AppException("name exists");
//        }
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");

        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseID(currentUser.getWarehouse().getWarehouseID());

        Supplier supplier = Supplier.builder()
                .SupplierCode(supplierDto.getSupplierCode())
                .SupplierName(supplierDto.getSupplierName())
                .email(supplierDto.getEmail())
                .isActive(supplierDto.getIsActive())
                .phone(supplierDto.getPhone())
                .warehouse(warehouse)
                .createdBy(currentUser)
                .lastModifiedBy(currentUser)
                .district(District.builder().districtID(supplierDto.getDistrictId()).build())
                .streetAddress(supplierDto.getStreetAddress())
                .build();
        log.info("[Start save Supplier " + supplierDto.getSupplierName() + " to database]");
        supplierRepository.saveAndFlush(supplier);
        log.info("[End save Supplier " + supplierDto.getSupplierName() + " to database]");
        log.info("[End SupplierService - saveSupplier with SupplierName: " + supplierDto.getSupplierName() + "]");
    }

    @Override
    public void deleteSupplier(Long supplierDto) {

    }
}
