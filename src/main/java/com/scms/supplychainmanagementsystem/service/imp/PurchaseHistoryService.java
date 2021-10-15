package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.PurchaseHistoryDto;
import com.scms.supplychainmanagementsystem.entity.*;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.MaterialRepository;
import com.scms.supplychainmanagementsystem.repository.PurchaseHistoryRepostory;
import com.scms.supplychainmanagementsystem.service.IPurchaseHistory;
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
public class PurchaseHistoryService implements IPurchaseHistory {

    private PurchaseHistoryRepostory purchaseHistoryRepostory;
    private final UserCommon userCommon;

    @Override
    public Page<PurchaseHistory> getAllPurchaseHistory(String PurchaseHistoryName, Long warehouseId, Pageable pageble) {
        return null;
    }

    @Override
    public PurchaseHistory getPurchaseHistoryByIdInWarehouse(Long PurchaseHistoryId) {
        User currentUser = userCommon.getCurrentUser();
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        if(currentUser.getRole().getRoleID()!=1){
            purchaseHistory=purchaseHistoryRepostory.findByPurchaseIdInWarehouse(PurchaseHistoryId,currentUser.getWarehouse().getWarehouseID());
            }else{
                purchaseHistory= purchaseHistoryRepostory.findByPurchaseId(PurchaseHistoryId);}
        return purchaseHistory;
    }

    @Override
    public void updatePurchaseHistory(Long PurchaseHistoryId, PurchaseHistoryDto purchaseHistoryDto) {
        log.info("[Start PurchaseHistoryService - UpdatePurchase  to database ]");
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");

        Warehouse warehouse = new Warehouse();

        if(currentUser.getRole().getRoleID()!=1){
            warehouse.setWarehouseID(currentUser.getWarehouse().getWarehouseID());
            if(currentUser.getWarehouse().getWarehouseID()!=purchaseHistoryRepostory.findByPurchaseId(PurchaseHistoryId).getWarehouse().getWarehouseID()){
                throw new AppException("you cant update in another Warehouse");
            }
        }else{
            warehouse.setWarehouseID(purchaseHistoryDto.getWarehouseId());
        }

        Material material = new Material();
        material.setMaterialID(purchaseHistoryDto.getMaterialId());
        Supplier supplier = new Supplier();
        supplier.setSupplierId(purchaseHistoryDto.getSupplierId());

        PurchaseHistory purchaseHistory = PurchaseHistory.builder()
                .purchaseID(PurchaseHistoryId)
                .material(material)
                .supplier(supplier)
                .warehouse(warehouse)
                .PurchaseDate(purchaseHistoryDto.getPurchaseDate())
                .createdBy(currentUser)
                .lastModifiedBy(currentUser)
                .Quantity(purchaseHistoryDto.getQuantity())
                .UnitPrice(purchaseHistoryDto.getUnitPrice())
                .build();
        log.info("[Start Update PurchaseHistoryService  to database]");
        purchaseHistoryRepostory.saveAndFlush(purchaseHistory);
        log.info("[Start PurchaseHistoryService - UpdatePurchase  to database ]");
        log.info("[End PurchaseHistoryService -UpdatePurchase ]");
    }

    @Override
    public void savePurchaseHistory(PurchaseHistoryDto purchaseHistoryDto) {
        log.info("[Start PurchaseHistoryService - savePurchase  to database ]");
        log.info("[Start get current user]");
        User currentUser = userCommon.getCurrentUser();
        log.info("[End get current user : " + currentUser.getUsername() + "]");
        Material material = new Material();
        material.setMaterialID(purchaseHistoryDto.getMaterialId());
        Supplier supplier = new Supplier();
        supplier.setSupplierId(purchaseHistoryDto.getSupplierId());
        Warehouse warehouse = new Warehouse();


        if(currentUser.getRole().getRoleID()!=1){
            warehouse.setWarehouseID(currentUser.getWarehouse().getWarehouseID());
        }else{
            warehouse.setWarehouseID(purchaseHistoryDto.getWarehouseId());
        }

        PurchaseHistory purchaseHistory = PurchaseHistory.builder()
                .material(material)
                .supplier(supplier)
                .warehouse(warehouse)
                .PurchaseDate(purchaseHistoryDto.getPurchaseDate())
                .createdBy(currentUser)
                .lastModifiedBy(currentUser)
                .Quantity(purchaseHistoryDto.getQuantity())
                .UnitPrice(purchaseHistoryDto.getUnitPrice())
                .build();
        log.info("[Start save PurchaseHistoryService  to database]");
        purchaseHistoryRepostory.saveAndFlush(purchaseHistory);
        log.info("[Start PurchaseHistoryService - savePurchase  to database ]");
        log.info("[End PurchaseHistoryService - savePurchase ]");
    }

    @Override
    public void deletePurchaseHistory(Long PurchaseHistoryId) {
        User currentUser = userCommon.getCurrentUser();
        if(currentUser.getRole().getRoleID()!=1){
            purchaseHistoryRepostory.deletePurchase(PurchaseHistoryId,currentUser.getWarehouse().getWarehouseID());}
        else{
            purchaseHistoryRepostory.deletePurchaseAdmin(PurchaseHistoryId);
        }
    }
}
