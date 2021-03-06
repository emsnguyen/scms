package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.PurchaseDto;
import com.scms.supplychainmanagementsystem.entity.Purchase;
import com.scms.supplychainmanagementsystem.entity.Supplier;
import com.scms.supplychainmanagementsystem.entity.User;
import com.scms.supplychainmanagementsystem.entity.Warehouse;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.PurchaseDetailRepository;
import com.scms.supplychainmanagementsystem.repository.PurchaseRepository;
import com.scms.supplychainmanagementsystem.repository.SupplierRepository;
import com.scms.supplychainmanagementsystem.repository.WarehouseRepository;
import com.scms.supplychainmanagementsystem.service.IPurchaseService;
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
public class PurchaseService implements IPurchaseService {

    private final UserCommon userCommon;
    private PurchaseRepository purchaseRepository;
    private PurchaseDetailRepository purchaseDetailRepository;
    private WarehouseRepository warehouseRepository;
    private SupplierRepository supplierRepository;

    @Override
    public Page<Purchase> getAllPurchase(Long warehouseId, Pageable pageable) {
        log.info("[Start PurchaseService - Get All Purchase]");
        Page<Purchase> PurchasePage;
        User current = userCommon.getCurrentUser();
        Warehouse wh = current.getWarehouse();
        Long userId = current.getUserId();
        if (current.getRole().getRoleID() == 1) {
            PurchasePage = purchaseRepository.filterAllWarehouses(warehouseId, pageable);
        } else {
            PurchasePage = purchaseRepository.filterInOneWarehouse(wh.getWarehouseID(), pageable);
        }
        log.info("[End PurchaseService - Get All Purchase]");
        return PurchasePage;
    }

    @Override
    public Purchase getPurchaseByIdInWarehouse(Long PurchaseId) {
        User currentUser = userCommon.getCurrentUser();
        Purchase purchase = new Purchase();
        if (currentUser.getRole().getRoleID() != 1) {
            purchase = purchaseRepository.findByPurchaseIdInWarehouse(PurchaseId, currentUser.getWarehouse().getWarehouseID());
        } else {
            purchase = purchaseRepository.findByPurchaseId(PurchaseId);
        }
        return purchase;
    }

    @Override
    public void updatePurchase(Long PurchaseId, PurchaseDto purchaseDto) {
        log.info("[Start PurchaseService - UpdatePurchase  to database ]");
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");

        Warehouse warehouse = new Warehouse();

        if (currentUser.getRole().getRoleID() != 1) {
            warehouse=warehouseRepository.getById(currentUser.getWarehouse().getWarehouseID());
            if (currentUser.getWarehouse().getWarehouseID() != purchaseRepository.findByPurchaseId(PurchaseId).getWarehouse().getWarehouseID()) {
                throw new AppException("you cant update in another Warehouse");
            }
        } else {
            warehouse=warehouseRepository.getById(purchaseDto.getWarehouseId());
        }
        Supplier supplier = new Supplier();
        supplier=supplierRepository.getById(purchaseDto.getSupplierId());


        Purchase purchase = Purchase.builder()
                .purchaseID(PurchaseId)
                .supplier(supplier)
                .warehouse(warehouse)
                .purchaseDate(purchaseDto.getPurchaseDate())
                .createdBy(currentUser)
                .lastModifiedBy(currentUser)
                .createdDate(Instant.now())
                .lastModifiedDate(Instant.now())
                .build();
        log.info("[Start Update PurchaseService  to database]");
        purchaseRepository.save(purchase);
        log.info("[Start PurchaseService - UpdatePurchase  to database ]");
        log.info("[End PurchaseService -UpdatePurchase ]");
    }

    @Override
    public void savePurchase(PurchaseDto purchaseDto) {
        log.info("[Start PurchaseService - savePurchase  to database ]");
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");

        Supplier supplier = new Supplier();
        supplier=supplierRepository.getById(purchaseDto.getSupplierId());

        Warehouse warehouse = new Warehouse();

        if (currentUser.getRole().getRoleID() != 1) {
            warehouse=warehouseRepository.getById(currentUser.getWarehouse().getWarehouseID());
        } else {
            warehouse=warehouseRepository.getById(purchaseDto.getWarehouseId());
        }

        Purchase purchase = Purchase.builder()
                .supplier(supplier)
                .warehouse(warehouse)
                .purchaseDate(purchaseDto.getPurchaseDate())
                .createdBy(currentUser)
                .lastModifiedBy(currentUser)
                .createdDate(Instant.now())
                .lastModifiedDate(Instant.now())
                .build();
        log.info("[Start save PurchaseService  to database]");
        purchaseRepository.saveAndFlush(purchase);
        log.info("[Start PurchaseService - savePurchase  to database ]");
        log.info("[End PurchaseService - savePurchase ]");
    }

    @Override
    public void deletePurchase(Long PurchaseId) {
        User currentUser = userCommon.getCurrentUser();
        if (currentUser.getRole().getRoleID() != 1) {
            purchaseDetailRepository.deletePurchaseDetailByPurchaseId(PurchaseId);
            purchaseRepository.deletePurchase(PurchaseId, currentUser.getWarehouse().getWarehouseID());
        } else {
            purchaseDetailRepository.deletePurchaseDetailByPurchaseId(PurchaseId);
            purchaseRepository.deletePurchaseAdmin(PurchaseId);
        }
    }
}
