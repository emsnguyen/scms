package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.PurchaseDto;
import com.scms.supplychainmanagementsystem.dto.PurchaseHistoryDto;
import com.scms.supplychainmanagementsystem.entity.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPurchaseService {

    Page<Purchase> getAllPurchase(Long warehouseId, Pageable pageble);

    Purchase getPurchaseByIdInWarehouse(Long PurchaseId);

    void updatePurchase(Long PurchaseId, PurchaseDto purchaseDto);

    void savePurchase(PurchaseDto purchaseDto);

    void deletePurchase(Long PurchaseId);
}
